package torneo.postas
import types.types._
import participante._

case class Pesca(capacidadMinima: Double) extends Posta(
  List[Condicion](_.capacidadDeCarga >= capacidadMinima),
  _.capacidadDeCarga >= _.capacidadDeCarga
)