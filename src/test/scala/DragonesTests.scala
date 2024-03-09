import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import types.types._
import participante._
import participante.dragon._
import participante.dragon.dragones._
import participante.items._
import participante.vikingos._

class DragonesTests extends AnyFreeSpec{

  "Dragones:" - {

    "La velocidad base normalmente es 60 km/h" in {
      //textual enunciado
      val peso: Double = 15.0
      NadderMortifero(peso).velocidadBase should be(60.0)
    }

    "los dragones vuelan a una velocidad equivalente a su velocidad base - su propio peso" in {
      //textual enunciado
      val peso: Double = 15.0
      NadderMortifero(peso).velocidad should be(60.0 - peso)
    }

    "Furia Nocturna" - {
      "levantan el triple de velocidad que la mayoría de los dragones" in {
        //textual enunciado
        val danio: Double = 20.0
        val peso: Double = 15.0
        FuriaNocturna(peso, danio).velocidad should be(3 * NadderMortifero(peso).velocidad)
      }
      "El daño que producen se indica para cada uno" in {
        //textual enunciado
        val danio: Double = 20.0
        val peso: Double = 15.0
        FuriaNocturna(peso, danio).danio should be(danio)
      }
    }

    "Nadder Mortifero" - {
      "Producen un daño de 150" in {
        //textual enunciado
        val peso: Double = 15.0
        NadderMortifero(peso).danio should be(150.0)
      }
    }
    "Gronckle" - {
      "su velocidad base es la mitad que la del resto de los dragones" in {
        //textual enunciado
        val peso: Double = 15.0
        Gronckle(peso, 15.0).velocidadBase should be(30.0)
        Gronckle(peso, 15.0).velocidad should be(30.0 - peso)

      }
      "el daño que realizan es de 5 veces su peso" in {
        //textual enunciado
        val peso: Double = 15.0
        Gronckle(peso, 15).danio should be(peso * 5)
      }

    }

  }

  "Requisitos de montura:" - {
    "los dragones sólo pueden cargar el 20% de su propio peso" in {
      //textual enunciado
      val peso: Double = 25.0
      val vikingoLiviano = new Vikingo(peso * 0.2, 5, 5, Arma(5))
      val vikingoPesado = new Vikingo(peso * 0.21, 5, 5, Arma(5))
//      capacidad de carga:
      FuriaNocturna(peso, 20.0).capacidadDeCarga should be(peso * 0.2)
//      puede ser montado:
      FuriaNocturna(peso, 20.0).puedeSerMontadoPor(vikingoLiviano) should be(true)
      FuriaNocturna(peso, 20.0).puedeSerMontadoPor(vikingoPesado) should be(false)

    }
    "Chimuelo es un Furia nocturna que requiere que el vikingo tenga un sistema de vuelo como ítem" in {
      //textual enunciado
      Chimuelo.puedeSerMontadoPor(Hipo) should be(true)
      Chimuelo.puedeSerMontadoPor(Patan) should be(false)

    }

    "Los Nadder mortíferos, como son muy vanidosos, siempre tienen una segunda restricción básica y es que el daño que puede hacer un vikingo no supere el suyo" in {
      //textual enunciado
      val peso: Double = 25.0
      val vikingoMuyFuerte = new Vikingo(5, 5, 21, Arma(130))
      val vikingoNoTanFuerte = new Vikingo(5, 5, 1, SistemaDeVuelo)
      NadderMortifero(peso).puedeSerMontadoPor(vikingoMuyFuerte) should be(false)
      NadderMortifero(peso).puedeSerMontadoPor(vikingoNoTanFuerte) should be(true)

    }
    "Los Gronckle en cambio, como son muy pesados, necesitan un vikingo que no supere un peso determinado para montarlos" in {
      //textual enunciado
      val capacidad: Double = 15
      val vikingoLiviano = new Vikingo(capacidad, 5, 5, Arma(5))
      val vikingoPesado = new Vikingo(capacidad + 1, 5, 5, Arma(5))
      Gronckle(25.0, capacidad).puedeSerMontadoPor(vikingoLiviano) should be(true)
      Gronckle(25.0, capacidad).puedeSerMontadoPor(vikingoPesado) should be(false)
    }


  }
}