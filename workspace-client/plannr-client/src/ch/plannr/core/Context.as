package ch.plannr.core
{

	import ch.plannr.model.Team;
	import ch.plannr.model.User;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
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
		
		[Bindable]
		public var selectedTeam:Team = null;
		
		[Bindable]
		public var ownedTeams:ArrayCollection = null;
		
		[Bindable]
		public var searchedUsers:ArrayCollection = null;
		
		[Bindable]
		public var selectUsers:ArrayCollection = null;
		
	}
}