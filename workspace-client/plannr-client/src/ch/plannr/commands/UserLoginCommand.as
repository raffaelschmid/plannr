package ch.plannr.commands

{
	import ch.plannr.model.User;
	
	import flash.events.EventDispatcher;
	
	import mx.events.DynamicEvent;
	import mx.rpc.IResponder;

	public class UserLoginCommand extends EventDispatcher implements IResponder
	{
		public function execute (params:Object):void
		{
			//we are not doing anything to the object yet so we will just pass it back
			result(params.data);
		}
		
		public function result (data:Object):void
		{
						
			var sEvt:DynamicEvent = new DynamicEvent("result");
			sEvt.data = new User("fn","ln","em","pw");
			
			dispatchEvent(sEvt);
		}
		
		public function fault (info:Object):void
		{
		}
	}
}