package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class UserLoginEvent extends Event
	{
		
		private var _email:String;
		private var _password:String;
		
		public function UserLoginEvent(email:String, password:String)
		{
			super(Events.LOGIN_ATTEMPT,true);
			this._email=email;
			this._password=password;
		}
		
		public function get password():String
		{
			return _password;
		}
		public function get email():String
		{
			return _email;
		}
		
	}
}