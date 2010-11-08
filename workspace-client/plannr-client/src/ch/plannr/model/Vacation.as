package ch.plannr.model
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.controls.Alert;
	import mx.controls.DateField;
	import mx.formatters.DateFormatter;
	import mx.rpc.xml.SimpleXMLEncoder;
	
	[Bindable]
	public class Vacation
	{
		private var _id:int = 0;
		private var _summary:String = "";
		private var _description:String="";
		private var _startTime:Date = null;
		private var _endTime:Date = null;
		private var _df:DateFormatter = null;
		
		public function Vacation(startTime:Date, endTime:Date,summary:String="",description:String="")
		{
			this._startTime=startTime;
			this._endTime=endTime;
			this._summary=summary;
			this._description=description;
			
		}
		
		public function get startTime():Date
		{
			return _startTime;
		}

		public function get endTime():Date
		{
			return _endTime;
		}
		
		public function set startTime(value:Date):void
		{
			_startTime= value;
		} 

		public function set endTime(value:Date):void
		{
			_endTime= value;
		} 
		
		public function set description(value:String):void
		{
			_description= value;
		} 
		
		public function get summary():String
		{
			return _summary;
		}
		
		public function set summary(value:String):void
		{
			_summary= value;
		} 
		public function get description():String
		{
			return _description;
		}
		public function get id():int
		{
			return _id;
		}
		public function set id(value:int):void
		{
			_id= value;
		} 
		
		public function toEventXml():XML{
			var addedItem:XML= <event/>;
			addedItem.@id = _id;
			addedItem.@startTime = _startTime;              
			addedItem.@endTime = _endTime;
			addedItem.@summary = _summary;
			addedItem.@description = _description;
			return addedItem;
		}
		
		public function toXml():XML {
			var vacation:XML = <vacation />
			vacation.id=id;	
			vacation.from = DateField.dateToString(startTime, "YYYYMMDD");
			vacation.to = DateField.dateToString(endTime, "YYYYMMDD");
			vacation.title = summary;
			return vacation;
		}
		
		public static function fromXml(xml:XML):Vacation{
			var vacation:Vacation = new Vacation(DateField.stringToDate(xml.from, "YYYYMMDD"), DateField.stringToDate(xml.to, "YYYYMMDD"),xml.title);
			vacation.id=parseInt(xml.id);
			return vacation;
		}
		
		public function toString():String{
			return "Vacation[startTime=" + startTime+",endTime=" + endTime+",summary=" + summary+",description=" + description + "]"
		}
	}
}