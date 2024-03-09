package participante.dragon

import participante.Vikingo

case class NadderMortifero(peso: Double, requisitosMontura: RequisitosMontura = RequisitoMonturaVacio) extends Dragon{

  override def danio: Double = 150

  override def puedeSerMontadoPor(vikingo: Vikingo): Boolean =
    super.puedeSerMontadoPor(vikingo) && vikingo.danio <= this.danio
}
