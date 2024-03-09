import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import types.types._
import participante._
import participante.dragon._
import participante.dragon.dragones._
import participante.items._
import participante.vikingos._
import torneo.postas._

class VikingosTests extends AnyFreeSpec {
  "Vikingos:" - {

    "un vikingo puede cargar un máximo de kilos de pescado equivalente al 50% de su peso + el doble de su barbarosidad" in {
      //textual enunciado
      ReiniciarVikingos()
      Patan.capacidadDeCarga should be(Patan.peso * 0.5 + Patan.barbarosidad * 2)

    }

    "El daño de un vikingo se calcula como su barbarosidad + el daño que produce el ítem que lleva" in {
      //textual enunciado
      ReiniciarVikingos()
      Patan.danio should be(Patapez.barbarosidad + 100)

    }

    "Para este torneo estarán participando los siguientes vikingos:" - {
      "Hipo" -{
        "que no tiene arma pero en vez de eso lleva un sistema de vuelo" in  {
          //textual enunciado
          ReiniciarVikingos()
          Hipo.item should be(SistemaDeVuelo)
        }
      }
      "Astrid" - {
        "que tiene un hacha que aumenta el daño del que la lleva en 30 puntos" in {
          //textual enunciado
          ReiniciarVikingos()
          Astrid.item should be(Arma(30))
        }
      }
      "Patán" - {
        "que tiene una maza que le suma 100 puntos de daño" in {
          //textual enunciado
          ReiniciarVikingos()
          Patan.item should be(Arma(100))
        }
      }
      "Patapez" - {
        "No puede participar en una posta si su hambre supera 50%" in {
          //textual enunciado
          ReiniciarVikingos()
          Patapez.participar(27.5)
          Patapez.hambre should be(50)
          Patapez.puedeParticiparEn(Pesca(0)) should be(true)

          ReiniciarVikingos()
          Patapez.participar(28)
          Patapez.hambre should be(51)
          Patapez.puedeParticiparEn(Pesca(0)) should be(false)
        }
        "le da el doble de hambre que al resto participar de una posta" in {
          //textual enunciado
          ReiniciarVikingos()
          Patapez.participarEn(Pesca(0)).asInstanceOf[Vikingo].hambre should be(5)
          ReiniciarVikingos()
          Patapez.participarEn(Combate(0)).asInstanceOf[Vikingo].hambre should be(15)
        }
        "leva un ítem comestible que come cada vez que termina de participar en una posta" in {
          //textual enunciado
          ReiniciarVikingos()
          Patapez.item should be(Comestible(5))
          //Astrid.copy(velocidadBase = 5) should be (Patan.copy(item = Arma(30)))
        }
      }

    }
  }
}