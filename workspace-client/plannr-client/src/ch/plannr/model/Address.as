package ch.plannr.model
{
	[Bindable]
	public class Address
	{
		public function Address()
		{
		}
		
		private var _street1:String;
		private var _street2:String;
		private var _zip:String;
		private var _city:String;
		private var _countryCode:String;
		
		
		public function set street1(value:String):void
		{
			_street1= value;
		}
		
		public function set street2(value:String):void
		{
			_street2= value;
		}
		
		public function set city(value:String):void
		{
			_city= value;
		}
		
		public function set countryCode(value:String):void
		{
			_countryCode= value;
		}
		
		public function set zip(value:String):void
		{
			_zip= value;
		} 
		
		public function get zip():String
		{
			return _zip;
		}
		
		public function get street1():String
		{
			return _street1;
		}
		
		public function get street2():String
		{
			return _street2;
		}
		
		public function get city():String
		{
			return _city;
		}
		
		public function get countryCode():String
		{
			return _countryCode;
		}
	}
}