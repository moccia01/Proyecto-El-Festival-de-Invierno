import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import types.types._
import participante._
import participante.dragon._
import participante.dragon.dragones._
import participante.items._
import participante.vikingos._
import torneo.Torneo
import torneo.postas._
import torneo.reglas.Reglas

class PostasTests extends AnyFreeSpec{

  "Postas:" - {

    "un competidor no puede participar en una posta si su nivel de hambre tras participar en la posta alcanzaría el 100%" in {
      //textual enunciado
      ReiniciarVikingos()

      val vikingoHambriento = new Vikingo(5, 5, 5, Arma(10), 99)
      val vikingos = List(vikingoHambriento, Patan, Patapez, Astrid)

      Pesca(0).puedenParticipar(vikingos).contains(vikingoHambriento) should be(false)

    }

    "Combate"- {
      "Debe tener al menos un grado de barbaridad mínimo o un arma equipada para participar de esta posta" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid, Jinete(Hipo, Chimuelo))

        Combate(2).puedenParticipar(vikingos).length should be(3)
        Combate(10).puedenParticipar(vikingos).length should be(2)


      }

      "se considera mejor el competidor que más daño pueda producir" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid)

        Combate(2).ganadores(vikingos).head should be(Patan)
        Combate(2).ganadores(vikingos)(1) should be(Astrid)
        Combate(2).ganadores(vikingos)(2) should be(Patapez)

      }

      "Cuando un vikingo participa de un combate incrementa en 10% su nivel de hambre" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid)

        Combate(2).ganadores(vikingos) //los hace participar
        Hipo.hambre should be(10)
        Patan.hambre should be(10)
        Patapez.hambre should be(15)//patapez aumenta el doble de hambre y come el comestible
        Astrid.hambre should be(10)


      }
    }//Terminan tests de  Combate

    "Pesca" - {
      "Puede existir o no un requerimiento de peso mínimo que debe levantar un participante" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid)

        Pesca(0).puedenParticipar(vikingos).length should be(4)
        Pesca(5).puedenParticipar(vikingos).length should be(3)

      }

      "en esta posta es mejor el competidor que más pescado logre cargar" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid)

        Pesca(0).ganadores(vikingos)(3) should be(Hipo)//Hipo es el que menos puede cargar

      }

      "Luego de participar en una posta de pesca los vikingos incrementan 5% su nivel de hambre. " in {
        //textual enunciado
        ReiniciarVikingos()

        Hipo.participarEn(Pesca(0))
        Hipo.hambre should be(5.0)
      }
    } //Terminan tests de Pesca

    "Carrera" - {
      "Puede requerir que el participante necesite una montura" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingos: Participantes = List(Hipo, Patan, Patapez, Astrid, Jinete(Hipo, Chimuelo))

        Carrera(requiereMontura = false, 5.0).puedenParticipar(vikingos).length should be(5)
        Carrera(requiereMontura = true, 5.0).puedenParticipar(vikingos).length should be(1)

      }

      "el mejor para una carrera es aquel que sea más veloz" in {
        //textual enunciado
        ReiniciarVikingos()

        val participantes: List[Participante] = List(Hipo, Patan, Patapez, Astrid, Jinete(Hipo, Chimuelo))

        Carrera(requiereMontura = false, 5.0).ganadores(participantes).head should be(Hipo)
        Carrera(requiereMontura = false, 5.0).ganadores(participantes)(1) should be(Astrid)

      }
    } //Terminan tests de Carrera

    "Mejor Montura" - {
      "Saber a partir de un conjunto de dragones que un vikingo puede llegar a montar, como le conviene participar en una posta" in {
        //textual enunciado
        ReiniciarVikingos()

        val vikingoQueNoLeCombieneUnGronkcle = new Vikingo(30, 5, 10, Arma(10))

        Carrera(requiereMontura = false, 10).mejorFormaDeParticiparOption(Hipo, List(Chimuelo)).orNull should be(Jinete(Hipo, Chimuelo))
        Carrera(requiereMontura = false, 10).mejorFormaDeParticiparOption(Patapez, List(Chimuelo)).orNull should be(Patapez)
        Combate(0).mejorFormaDeParticiparOption(Astrid, List(NadderMortifero(30), FuriaNocturna(30, 50), Chimuelo)).orNull should be(Jinete(Astrid, NadderMortifero(30)))
        Pesca(0).mejorFormaDeParticiparOption(vikingoQueNoLeCombieneUnGronkcle, List(Gronckle(29, 5))).orNull should be(vikingoQueNoLeCombieneUnGronkcle)
      }

      "Preparacion debe retornar los participantes que competiran en la posta decidiendo cómo hacerlo (Jinete o Vikingo)" in {
        //No es textual
        ReiniciarVikingos()

        val vikingoConArmaFuerte = new Vikingo(5, 5, 5, Arma(200))
        val vikingos: Vikingos = List(Hipo, Astrid, vikingoConArmaFuerte, Patapez)
        val dragones: Dragones = List(Chimuelo.copy(danioBase = 999), Gronckle(5, 100), NadderMortifero(30), NadderMortifero(30))
        val torneo: Torneo[Vikingo] = new Torneo(List(Combate(0)), vikingos, dragones, new Reglas())

        val participantes: Participantes = torneo.elegirDragones(Combate(0), vikingos)

        Combate(0).ganadores(participantes) should be(List(Hipo, vikingoConArmaFuerte, Astrid, Patapez))
      }
    }

  }

}
