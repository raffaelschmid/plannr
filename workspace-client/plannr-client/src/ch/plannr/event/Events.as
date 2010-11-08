package ch.plannr.event
{
	public class Events
	{
		public static const LOGIN_ATTEMPT:String="LOGIN_ATTEMPT";
		public static const LOGIN_SUCCESS:String="LOGIN_SUCCESS";
		public static const LOGIN_FAILURE:String="LOGIN_FAILURE";
		
		public static const OWNED_TEAMS_LOAD_FAILURE:String="OWNED_TEAMS_LOAD_FAILURE";
		public static const OWNED_TEAMS_LOAD_SUCCESS:String="OWNED_TEAMS_LOAD_SUCCESS";
		public static const OWNED_TEAMS_SELECTED:String="OWNED_TEAMS_SELECTED";

		public static const OWNED_TEAMS_DELETE:String="OWNED_TEAMS_DELETE";
		
		public static const OWNED_TEAMS_CREATE:String="OWNED_TEAMS_CREATE";
		public static const OWNED_TEAMS_CREATE_SUCCESS:String="OWNED_TEAMS_CREATE_SUCCESS";
		public static const OWNED_TEAMS_CREATE_FAILURE:String="OWNED_TEAMS_CREATE_FAILURE";
		
		public static const OWNED_TEAMS_UPDATE:String="OWNED_TEAMS_UPDATE";
		public static const OWNED_TEAMS_UPDATE_SUCCESS:String="OWNED_TEAMS_UPDATE_SUCCESS";
		public static const OWNED_TEAMS_UPDATE_FAILURE:String="OWNED_TEAMS_UPDATE_FAILURE";

		public static const SEARCH_USERS:String="SEARCH_USERS";

		public static const MEMBERS_ADD_EVENT:String="MEMBERS_ADD_EVENT";
		public static const MEMBERS_ADD_EVENT_SUCCESS:String="MEMBERS_ADD_EVENT_SUCCESS";
		public static const MEMBERS_ADD_EVENT_FAILURE:String="MEMBERS_ADD_EVENT_FAILURE";
		
		public static const MEMBERS_REMOVE_EVENT:String="MEMBERS_REMOVE_EVENT";
		public static const MEMBERS_REMOVE_EVENT_SUCCESS:String="MEMBERS_REMOVE_EVENT_SUCCESS";
		public static const MEMBERS_REMOVE_EVENT_FAILURE:String="MEMBERS_REMOVE_EVENT_FAILURE";
		
		public static const VACATION_SAVE:String="VACATION_SAVE";
		public static const VACATION_SAVE_SUCCESS:String="VACATION_SAVE_SUCCESS";
		public static const VACATION_SAVE_FAILURE:String="VACATION_SAVE_FAILURE";
		
		public static const VACATIONS_LOAD:String="VACATIONS_LOAD";
		public static const VACATIONS_LOAD_SUCCESS:String="VACATIONS_LOAD_SUCCESS";
		public static const VACATIONS_LOAD_FAILURE:String="VACATIONS_LOAD_FAILURE";
		
		public static const VACATION_DELETE:String="VACATION_DELETE";
		public static const VACATION_DELETE_SUCCESS:String="VACATION_DELETE_SUCCESS";
		public static const VACATION_DELETE_FAILURE:String="VACATION_DELETE_FAILURE";
	}
}