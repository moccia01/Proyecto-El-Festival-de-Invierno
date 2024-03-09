import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import types.types._
import participante.vikingos._
import torneo.reglas.Eliminacion

class ReglasTest extends AnyFreeSpec{

  "Reglas:" - {

      "Eliminacion" - {

          "se elimina los ultimos n participantes" in {
            var vikingos: Vikingos = List(Hipo, Patan, Patapez, Astrid)

            vikingos = Eliminacion(3).superanLaPosta(vikingos)
            vikingos.length should be(1)

          }


      }

  }

}
