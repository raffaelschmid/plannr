package ch.plannr.event
{
	import ch.plannr.model.Vacation;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	public class LoadVacationsEvent extends Event
	{
		private var _vacations:ArrayCollection;
		
		public function LoadVacationsEvent(id:String, vacations:ArrayCollection=null)
		{
			super(id,true);
			this._vacations=vacations;
		}
		
		public function get vacations():ArrayCollection
		{
			return _vacations;
		}
		
	}
}