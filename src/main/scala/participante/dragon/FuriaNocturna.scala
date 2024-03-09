package participante.dragon

import participante.Vikingo
import participante.items.SistemaDeVuelo

case class FuriaNocturna(peso: Double, danioBase: Double, requisitosMontura: RequisitosMontura = RequisitoMonturaVacio) extends Dragon{

  def danio: Double = danioBase
  override def velocidad: Double = super.velocidad * 3
}
