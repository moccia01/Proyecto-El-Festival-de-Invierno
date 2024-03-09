package torneo.postas
import types.types._
import participante._
import participante.items.Arma

case class Combate(barbarosidadMinima: Double) extends Posta(
  List[Condicion] (
    {
//    TODO podra hacerse con pattermatching ej: vikingo.item == Arma(_)
      case vikingo: Vikingo => vikingo.barbarosidad >= barbarosidadMinima || vikingo.item.isInstanceOf[Arma]
      case jinete: Jinete => jinete.vikingo.barbarosidad >= barbarosidadMinima || jinete.vikingo.item.isInstanceOf[Arma]
    }
  ),
  _.danio >= _.danio
)