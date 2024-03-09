package participante.dragon.dragones
import participante._
import participante.dragon._
import participante.items._

object Chimuelo extends FuriaNocturna(
  25,
   5,
  RequisitosMontura(Set[(Vikingo, Dragon) => Boolean]((vikingo, _) => {
    val item: Item = vikingo.item
    item match {
      case SistemaDeVuelo => true
      case _ => false
    }
  }
  )
  )
)