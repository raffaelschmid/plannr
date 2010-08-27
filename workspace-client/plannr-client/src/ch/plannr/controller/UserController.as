package ch.plannr.controller
{
	import ch.plannr.event.CustomEvent;
	import ch.plannr.event.Events;
	import ch.plannr.model.User;
	import ch.plannr.service.HttpServiceFactory;
	
	import flash.events.IEventDispatcher;
	import flash.net.URLRequest;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	import org.swizframework.utils.services.ServiceHelper;

	public class UserController
	{
		
		[Dispatcher]
		public var dispatcher:IEventDispatcher;
		
		[Inject]
		public var serviceHelper : ServiceHelper;
		
		[Inject]
		public var httpServiceFactory:HttpServiceFactory;
		
		[Bindable]
		[Inject(source="context.currentUser", bind="true", twoWay="true")]
		public var currentUser : User;
		
		[Bindable]
		[Inject(source="context.email", bind="true", twoWay="true")]
		public var email:String;
		
		[Bindable]
		[Inject(source="context.password", bind="true", twoWay="true")]
		public var password:String;
		
		[Bindable]
		[Inject(source="context.searchedUsers", bind="true", twoWay="true")]
		public var searchedUsers:ArrayCollection;
		
		
		
		
		
		[Mediate( event="Events.REGISTRATION_ATTEMPT", properties="user" )]
		public function registerUser( user : User ) : void
		{
			serviceHelper.executeServiceCall( httpServiceFactory.postService("/webservices/register",true).send(user.toXml()), handleRegistrationResult );
		}

		private function handleRegistrationResult( event : ResultEvent ) : void
		{
			var xml:XML = new XML(event.result);
			dispatcher.dispatchEvent(new CustomEvent((xml.@success=='true')?Events.REGISTRATION_SUCCESS:Events.REGISTRATION_FAILURE));
		}
		

		[Mediate( event="Events.LOGIN_ATTEMPT", properties="email,password" )]
		public function loginUser( email:String, password:String ) : void
		{
			// bind email and password, will be used for login
			this.email=email;
			this.password=password;
			
			function handleLoginResult( event : ResultEvent ) : void
			{
				var xml:XML = new XML(event.result);
				var list:XMLList = xml.user;
				currentUser = User.fromXml(list[0]);
				dispatcher.dispatchEvent(new CustomEvent((xml.@success=='true')?Events.LOGIN_SUCCESS:Events.LOGIN_FAILURE));
			}
			
			function handleLoginFault( event : FaultEvent ) : void
			{
				dispatcher.dispatchEvent(new CustomEvent(Events.LOGIN_FAILURE));
			}
			
			serviceHelper.executeServiceCall( httpServiceFactory.postService("/webservices/login",true).send(<null/>), handleLoginResult,handleLoginFault );
		}
		
		[Mediate( event="Events.SEARCH_USERS", properties="term" )]
		public function searchUsers( term:String) : void
		{
			function handleLoginResult( event : ResultEvent ) : void
			{
				var xml:XML = new XML(event.result);
				var users:XMLList = xml.users.user;
				
				var list:ArrayCollection = new ArrayCollection();
				for each(var user:XML in users) {
					list.addItem(User.fromXml(user));
				}
				searchedUsers = list;
				
			}
			
			function handleLoginFault( event : FaultEvent ) : void
			{
				dispatcher.dispatchEvent(new CustomEvent(Events.LOGIN_FAILURE));
			}
			
			serviceHelper.executeServiceCall( httpServiceFactory.getService("/webservices/search/user?term="+term,true).send(), handleLoginResult,handleLoginFault );
		}
		
	}
}