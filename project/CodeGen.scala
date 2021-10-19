import sbt.File
import sbt.io.IO

object CodeGen {

  def writeFiles(sourceDir: File): Seq[File] = {
    Seq("Alpha", "Bravo", "Charlie").map { name =>
      val outFile = new File(sourceDir, name + ".scala")
      IO.write(outFile, "case class " + name + "(thing: String)")
      outFile
    }

  }

}
