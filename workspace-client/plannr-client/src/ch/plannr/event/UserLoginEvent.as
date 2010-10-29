package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class UserLoginEvent extends Event
	{
		
		public function UserLoginEvent()
		{
			super(Events.LOGIN_SUCCESS,true);
		}		
	}
}