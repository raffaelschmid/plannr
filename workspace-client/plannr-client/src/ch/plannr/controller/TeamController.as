package ch.plannr.controller
{
	import ch.plannr.event.CustomEvent;
	import ch.plannr.event.Events;
	import ch.plannr.model.Team;
	import ch.plannr.model.User;
	import ch.plannr.service.HttpServiceFactory;
	
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import org.swizframework.utils.services.ServiceHelper;

	public class TeamController
	{
		[Dispatcher]
		public var dispatcher:IEventDispatcher;
		
		[Inject]
		public var serviceHelper : ServiceHelper;
		
		[Inject]
		public var httpServiceFactory:HttpServiceFactory;
		
		[Bindable]
		[Inject(source="context.currentUser",bind="true")]
		public var currentUser : User;

		[Bindable]
		[Inject(source="context.ownedTeams",bind="true", twoWay="true")]
		public var ownedTeams : ArrayCollection;
		
		[Inject(source="context.selectedTeam", bind="true",twoWay="true")]
		public var selectedTeam:Team = null;
		
		[Mediate( event="Events.LOGIN_SUCCESS")]
		public function loadOwnedTeams() : void
		{
			function handleResult( event : ResultEvent ) : void
			{
				var xml:XML = new XML(event.result);
				var teams:XMLList = xml.teams.team;
				var list:ArrayCollection = new ArrayCollection();
				for each(var team:XML in teams) {
					list.addItem(Team.fromXml(team));
				}
				ownedTeams = list;
			}
			
			function handleFault( event : FaultEvent ) : void
			{
				dispatcher.dispatchEvent(new CustomEvent(Events.OWNED_TEAMS_LOAD_FAILURE));
			}
			
			serviceHelper.executeServiceCall( httpServiceFactory.getService("/webservices/team?ownerId="+currentUser.id,true).send(), handleResult,handleFault );
		}
		
		[Mediate(event="Events.OWNED_TEAMS_DELETE",properties="team")]
		public function deleteOwnedTeam(team:Team):void{
			function handleResult( event : ResultEvent ) : void
			{
				var itemIndex:int = ownedTeams.getItemIndex(team);
				ownedTeams.removeItemAt(itemIndex);
			}
			
			function handleFault( event : FaultEvent ) : void
			{
				dispatcher.dispatchEvent(new CustomEvent(Events.OWNED_TEAMS_LOAD_FAILURE));
			}
			
			serviceHelper.executeServiceCall( httpServiceFactory.deleteService("/webservices/team/"+team.id,true).send(), handleResult,handleFault );
			
		}
	}
}