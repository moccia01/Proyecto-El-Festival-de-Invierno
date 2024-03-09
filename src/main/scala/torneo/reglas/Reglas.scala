package torneo.reglas

import participante._
import torneo.postas._
import types.types._
import scala.util.{ Try, Success, Failure}


class Reglas[T] {

  def elegirDragones(vikingos: Vikingos, dragones: Dragones, posta: Posta): Participantes = {
    var dragonesDisponibles = dragones
    vikingos.flatMap({v =>
      val participante: Option[Participante] = posta.mejorFormaDeParticiparOption(v, dragonesDisponibles) //flatMap se queda solo con los valores q haya en un Some, si es None lo ignora
      val dragonMontado = List(participante.map(_.drake()).orNull) //Usa un mensaje polimorfico (sigue usando match pero solo en la implementacion del metodo dentro de participante)
      dragonesDisponibles = dragonesDisponibles.diff(dragonMontado) //si hay dragon lo saca y si hay null queda la lista igual
      participante
    })
    }

  def superanLaPosta(participantes: Vikingos) = { //quiénes se desempeñaron lo suficientemente bien para pasar a la siguiente
    participantes.take(obtenerLaMitadDeLaLista(participantes))
  }

  def elegirGanador(participantes: Vikingos): Option[T] = { //Huele raro esto
    participantes.map(_.asInstanceOf[T]).headOption

  }
  def flat(competidores: List[T]): Vikingos ={ //Huele raro esto
    competidores.map(_.asInstanceOf[Vikingo])
  }
  def obtenerLaMitadDeLaLista(participantes: Vikingos): Int = {
    //redondeo hacia arriba para evitar que si un solo jugador participa en la posta, no la supere por ser eliminado
    scala.math.ceil(participantes.length / 2).toInt
  }

  def quedanSuficientesParticipantes(vikingos: Vikingos): Boolean = { //Recibe List[T]
    vikingos.size > 1
  }
}
