package ch.plannr.core
{

	import ch.plannr.model.User;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.events.PropertyChangeEvent;
	import mx.utils.Base64Encoder;
		
	public class Context extends EventDispatcher
	{
		[Bindable]
		public var currentUser:User = null;
		
		[Bindable]
		public var email:String = null;
		
		[Bindable]
		public var password:String = null;
		
		
		/*public function getAuthorizationHeaders():Array
		{
			var headers:Array = new Array();
			var encoder:Base64Encoder=new Base64Encoder();
			encoder.encode(_email + ":" + _password);
			headers["Authorization"]="Basic " + encoder.toString();
			return headers;
		}*/
	}
}