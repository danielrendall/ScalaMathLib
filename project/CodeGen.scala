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

  def writeFiles(sourceDir: File): Seq[File] = {
    polygons.map { polygon =>
      val outFile = new File(sourceDir, "uk/co/danielrendall/mathlib/geom2d/shapes/" +  polygon.name + ".scala")

      val paramNames: Seq[String] = (1 to polygon.numSides).map(n => "p" + n)
      val paramNamePairs: Seq[(String, String)] = paramNames.zip(paramNames.tail.:+(paramNames.head))

      object sym {
        val Geom2dPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.geom2d")
        val ShapesPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.geom2d.shapes")
        val UtilPackage: ModuleSymbol = RootClass.newPackage("uk.co.danielrendall.mathlib.util")
        val PolyClass: ClassSymbol = RootClass.newClass(polygon.name)
        val PointClass: ClassSymbol = RootClass.newClass("Point")
        val ShapeClass: ClassSymbol = RootClass.newClass("Shape")
        val RadClass: ClassSymbol = RootClass.newClass("Rad")
        val VecClass: ClassSymbol = RootClass.newClass("Vec")
        val LineClass: ClassSymbol = RootClass.newClass("Line")
        val PolygonHelperClass: ClassSymbol = RootClass.newClass("PolygonHelper")
      }

      val params = paramNames.map(name => mkTreeFromDefStart(PARAM(name, sym.PointClass)))
      val tree = BLOCK(
        IMPORT(sym.Geom2dPackage, "_"),
        IMPORT(sym.UtilPackage, "Rad"),
        CASECLASSDEF(sym.PolyClass)
          .withParams(params)
          .withParents(sym.ShapeClass TYPE_OF sym.PolyClass,
            sym.PolygonHelperClass TYPE_OF sym.PolyClass) := BLOCK(

          VAL("points", TYPE_SEQ(sym.PointClass))
            .withFlags(Flags.OVERRIDE) :=
            SEQ(params.map(p => REF(p.name))),

          VAL("lines", TYPE_SEQ(sym.LineClass))
            .withFlags(Flags.OVERRIDE, Flags.LAZY) :=
            SEQ(paramNamePairs.map { case (pFirst, pSecond) => REF(sym.LineClass) APPLY (REF(pFirst), REF(pSecond))}),

          DEF("rotate", sym.PolyClass)
            .withFlags(Flags.OVERRIDE)
            .withParams(PARAM("angle", sym.RadClass), PARAM("about", sym.PointClass)) :=
          BLOCK (
            REF(sym.PolyClass) APPLY params.map { param =>
              REF(param.name) DOT "rotate" APPLY (REF("angle"), REF("about"))
            }
          ),

          DEF("translate", sym.PolyClass)
            .withFlags(Flags.OVERRIDE)
            .withParams(PARAM("vec", sym.VecClass)) := BLOCK (
            REF(sym.PolyClass) APPLY params.map { param =>
              REF(param.name) DOT "displace" APPLY REF("vec")
            }
          )
        )
      ).inPackage(sym.ShapesPackage)

      IO.write(outFile, treeToString(tree))
      outFile
    }

  }


  case class Polygon(name: String, numSides: Int)

}
