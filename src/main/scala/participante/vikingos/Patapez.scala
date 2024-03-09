package participante.vikingos
import participante._
import participante.items.Comestible
import torneo.postas.Posta

//TODO no se puede extender una case class, esto compila, pero pasa por los metodos de Vikingo
object Patapez extends Vikingo (5, 5, 5, Comestible(5)) {

  override def participar(incHambre: Double): Unit = {
    val saciedad: Double = item match {
      case Comestible(s) => s
      case _ => 0.0
    }
    hambre += (incHambre * 2 - saciedad).max(0)
  }

  override def puedeParticipar(incHambre: Double): Boolean = hambre <= 50

}