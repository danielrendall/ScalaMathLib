import sbt.File
import sbt.io.IO
import treehugger.forest._
import definitions._
import treehuggerDSL._


object CodeGen {

  private val polygons: Seq[Polygon] = Seq(
    "Triangle", "Quadrilateral", "Pentagon", "Hexagon", "Heptagon", "Octagon",
    "Nonagon", "Decagon", "Unidecagon", "Dodecagon", "Tridecagon", "Quadradecagon",
    "Pentadegacon", "Hexadecagon", "Heptadecagon", "Octadecagon", "Nonadecagon", "Icosagon"
  ).zipWithIndex.map { case (name, index) => Polygon(name, index + 3)}

  object sym {
    val Geom2dPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.geom2d")
    val ShapesPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.geom2d.shapes")
    val UtilPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.util")
    val PolygonClass: ClassSymbol = RootClass.newClass("Polygon")
    val GenericPolygonClass: ClassSymbol = RootClass.newClass("GenericPolygon")
    val PointClass: ClassSymbol = RootClass.newClass("Point")
    val ShapeClass: ClassSymbol = RootClass.newClass("Shape")
    val RadClass: ClassSymbol = RootClass.newClass("Rad")
    val VecClass: ClassSymbol = RootClass.newClass("Vec")
    val LineClass: ClassSymbol = RootClass.newClass("Line")
    val PolygonHelperClass: ClassSymbol = RootClass.newClass("PolygonHelper")
  }


  def writeFiles(sourceDir: File): Seq[File] = {
    val polys = writePolygons(sourceDir)
    val polyGen = writePolyGen(sourceDir)

    polys ++ polyGen
  }

  def writePolygons(sourceDir: File): Seq[File] = polygons.map { polygon =>
    val outFile = new File(sourceDir, "uk/co/danielrendall/mathlib/geom2d/shapes/" +  polygon.name + ".scala")

    object localsym {
      val PolyClass: ClassSymbol = RootClass.newClass(polygon.name)
    }

    val paramNames: Seq[String] = (1 to polygon.numSides).map(n => "p" + n)
    val paramNamePairs: Seq[(String, String)] = paramNames.zip(paramNames.tail.:+(paramNames.head))

    val params = paramNames.map(name => mkTreeFromDefStart(PARAM(name, sym.PointClass)))
    val tree = BLOCK(
      IMPORT(sym.Geom2dPackage, "_"),
      IMPORT(sym.UtilPackage, "Rad"),
      CASECLASSDEF(localsym.PolyClass)
        .withParams(params)
        .withParents(
          sym.PolygonClass,
          sym.ShapeClass TYPE_OF localsym.PolyClass,
          sym.PolygonHelperClass TYPE_OF localsym.PolyClass) := BLOCK(

        VAL("points", TYPE_SEQ(sym.PointClass))
          .withFlags(Flags.OVERRIDE) :=
          SEQ(params.map(p => REF(p.name))),

        VAL("lines", TYPE_SEQ(sym.LineClass))
          .withFlags(Flags.OVERRIDE, Flags.LAZY) :=
          SEQ(paramNamePairs.map { case (pFirst, pSecond) => REF(sym.LineClass) APPLY (REF(pFirst), REF(pSecond))}),

        DEF("rotate", localsym.PolyClass)
          .withFlags(Flags.OVERRIDE)
          .withParams(PARAM("angle", sym.RadClass), PARAM("about", sym.PointClass)) :=
          BLOCK (
            REF(localsym.PolyClass) APPLY params.map { param =>
              REF(param.name) DOT "rotate" APPLY (REF("angle"), REF("about"))
            }
          ),

        DEF("translate", localsym.PolyClass)
          .withFlags(Flags.OVERRIDE)
          .withParams(PARAM("vec", sym.VecClass)) := BLOCK (
          REF(localsym.PolyClass) APPLY params.map { param =>
            REF(param.name) DOT "displace" APPLY REF("vec")
          }
        )
      )
    ).inPackage(sym.ShapesPackage)

    IO.write(outFile, treeToString(tree))
    outFile
  }

  def writePolyGen(sourceDir: File): Seq[File] = {
    val outFile = new File(sourceDir, "uk/co/danielrendall/mathlib/geom2d/shapes/PolygonGen.scala")
    object localsym {
      val PolygenClass: ClassSymbol = RootClass.newClass("PolygonGen")
    }

    def makePolygonChecker(remainingPolygons: Seq[Polygon],
                           currentListName: String,
                           count: Int): Tree = BLOCK (
      remainingPolygons.headOption match {
        case Some(nextPoly) =>
          REF(currentListName) MATCH(
            CASE(REF("p" + count) INFIX(ConsClass) UNAPPLY REF("rest" + count)) ==>
              makePolygonChecker(remainingPolygons.tail, "rest" + count, count + 1),
            CASE(WILDCARD) ==>
              (REF(nextPoly.name) APPLY (1 until count).map(i => REF("p" + i)))
          )
        case None =>
          NEW(REF(sym.GenericPolygonClass), REF("p1") INFIX("::") APPLY(REF("p2") INFIX("::") APPLY(REF("p3") INFIX("::") APPLY(REF("rest")))))
      }
    )

    val tree = BLOCK(
      IMPORT(sym.Geom2dPackage, "_"),
      TRAITDEF(localsym.PolygenClass.name) := BLOCK (

        DEF("create", sym.PolygonClass)
          .withParams(mkTreeFromDefStart(PARAM("p1", sym.PointClass)),
            mkTreeFromDefStart(PARAM("p2", sym.PointClass)),
            mkTreeFromDefStart(PARAM("p3", sym.PointClass)),
              mkTreeFromDefStart(PARAM("rest", TYPE_LIST(sym.PointClass)))) :=
          makePolygonChecker(polygons, "rest", 4)

      )
    ).inPackage(sym.ShapesPackage)

    IO.write(outFile, treeToString(tree))
    Seq(outFile)
  }

  case class Polygon(name: String, numSides: Int)

}
