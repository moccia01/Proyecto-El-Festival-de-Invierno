package torneo.postas
import types.types._
import participante._

case class Carrera(requiereMontura: Boolean, distancia: Double) extends Posta(
  List[Condicion] (
    {
      case vikingo: Vikingo => !requiereMontura
      case Jinete(_, _) => true
    }
  ),
  _.velocidad >= _.velocidad
)

