package ch.plannr.commands
{
	import ch.plannr.client.events.UserRegisteredEvent;
	
	import mx.controls.Alert;

	public class UserLoggedinCommand
	{
		public function UserLoggedinCommand()
		{
		}
		public function execute(event:UserRegisteredEvent):void{
			Alert.show("hello" + event.user);
		}
	}
}