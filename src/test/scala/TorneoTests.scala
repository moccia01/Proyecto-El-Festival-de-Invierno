import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import torneo._
import torneo.postas._
import types.types._
import participante._
import participante.dragon._
import participante.vikingos._
import participante.dragon.dragones._
import participante.items._
import torneo.reglas._

class TorneoTests extends AnyFreeSpec{
  "Estandar" - {

    "Torneo Estandar" - {

      "De carrera" in {
        ReiniciarVikingos()

        val postas: Postas = List(Carrera(requiereMontura = false, 5.0))
        val participantes: List[Vikingo] = List(Hipo, Patan, Patapez, Astrid)
        val dragones: Dragones = List(Chimuelo)

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, Estandar())

        val ganador: Vikingo = torneo.obtenerGanador().get

        ganador should be(Hipo)
      }

      "Torneo con varias postas, participantes y dragones" in {
        ReiniciarVikingos()

        val postas: Postas = List(Combate(2), Combate(2))
        val participantes: List[Vikingo] = List(Hipo, Patan, Patapez, Astrid)
        val dragones: Dragones = List(Chimuelo, FuriaNocturna(20.0, 20.0), Gronckle(15.0, 15.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, Estandar())

        val ganador: Vikingo = torneo.obtenerGanador().get

        //TODO Chequear cual test fue correcto si este nuevo o el original que estaba en TorneoTests ya que cambió por el calculo de superanPosta

        ganador should be (Patan)  // 5.0 ya que en superanPosta, hace la mitad de 3, 1.5 redondeado a 2, 3 - 2 = 1. Gana Patan
      }

      " Si quedan varios participantes al terminar todas las postas gana " +
        "el que le haya ido mejor en la última" in {
        ReiniciarVikingos()

        val postas: Postas = List(Combate(2))
        val participantes: List[Vikingo] = List(Hipo, Patapez, Patan)
        val dragones: Dragones = List(Chimuelo, FuriaNocturna(20.0, 20.0), Gronckle(10.0, 15.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, Estandar())

        val ganador: Vikingo = torneo.obtenerGanador().get

        ganador should be(Patan)
      }
    }

    "Eliminacion" - {
      "en vez de avanzar la mitad a la siguiente posta, avanzan " +
        "todos excepto los últimos N, donde N se da en la regla. " in {
        ReiniciarVikingos()

        val eliminacion = Eliminacion(2)

        val postas: Postas = List(Combate(2))
        val participantes: List[Vikingo] = List(Hipo, Patapez, Patan, Astrid)
        val dragones: Dragones = List(Chimuelo, FuriaNocturna(20.0, 20.0), Gronckle(10.0, 15.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, eliminacion)

        val ganador: Vikingo = torneo.obtenerGanador().get

        ganador should be(Patan)
      }
    }

    "Torneo Inverso" - {

      "Avanza la mitad a la que peor le haya ido" in {
        ReiniciarVikingos()

        val inverso = new TorneoInverso

        val postas: Postas = List(Combate(0))
        val participantes: List[Vikingo] = List(Hipo, Patapez, Patan, Astrid)
        val dragones: Dragones = List(Chimuelo, FuriaNocturna(20.0, 20.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, inverso)

        val ganadores = torneo.hacerUnaPosta(Combate(0),participantes)//torneo.hacerUnaPosta(Combate(2), participantes)
        val maletas: Vikingos = List(Hipo, Patapez)

        ganadores should be(maletas)


      }

      "si terminan varios gana el que haya salido en último lugar en la posta final" in {
        ReiniciarVikingos()

        val inverso = new TorneoInverso
        val postas: Postas = List(Combate(2))
        val participantes: List[Vikingo] = List(Hipo, Patapez, Patan, Astrid)
        val dragones: Dragones = List(Chimuelo, FuriaNocturna(20.0, 20.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, inverso)
        val ganadores: Vikingo = torneo.obtenerGanador().get

        ganadores should be(Patapez)
      }

    }

    "Con Handicap" - {
      "Elijen dragones en el orden inverso al que terminaron" in {
        ReiniciarVikingos()

        val handicap = new Handicap

        val postas: Postas = List(Combate(2))
        val participantes: List[Vikingo] = List(Patapez, Patan, Hipo, Astrid)
        val dragones: Dragones = List(Gronckle(10.0, 15.0))

        val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, handicap)

        val maletasConDragones: Participantes = List(
          Jinete(Astrid, Gronckle(10.0, 15.0)),
          Patan
        )

        val vikingosPostPosta = torneo.hacerUnaPosta(Combate(0), participantes)
        val maletas = torneo.elegirDragones(Combate(2),vikingosPostPosta)
        maletas should be(maletasConDragones)

      }}

      "Con Veto" - {
        "de los dragones del torneo sólo están disponibles aquellos que cumplen cierta condición." in {
          ReiniciarVikingos()

          val veto = Veto(_.danio > 69)

          val postas: Postas = List(Carrera(requiereMontura = true, 2))

          val participantes: List[Vikingo] = List(Hipo, Patapez, Astrid)

          val dragones: Dragones = List(Chimuelo.copy(danioBase = 70), NadderMortifero(25.0), Gronckle(10.0, 15.0))

          val torneo: Torneo[Vikingo] = Torneo(postas, participantes, dragones, veto)


//          veto filtra correctamente los dragones (solo el gronckle tiene menos de 69 de danio)
          veto.dragonesDisponibles(dragones) should be (List(Chimuelo.copy(danioBase = 70), NadderMortifero(25.0)))

//          El más veloz es hipo con chimuelo
          torneo.obtenerGanador().get should be(Hipo)

        }
      }

      "Por Equipos" - {
        "equipos" in {
          val equipo1: Equipo = Equipo(List(Patan, Patapez))
          val equipo2: Equipo = Equipo(List(Astrid, Hipo))
          val equipos: List[Equipo] = List(equipo1, equipo2)
          val dragones: Dragones = List(Chimuelo.copy(danioBase = 120), NadderMortifero(25))

//          hipo con chimuelo y patan con el nadder deberían ganar el combate y luego hipo ganar la carrera => el equipo ganador debería ser Hipo + Astrid

          val torneo: Torneo[Equipo] = Torneo(List(Combate(0), Carrera(true, 10)), equipos, dragones, PorEquipos(equipos))

          torneo.obtenerGanador().get should be (equipo2)

        }
      }
    }
  }

