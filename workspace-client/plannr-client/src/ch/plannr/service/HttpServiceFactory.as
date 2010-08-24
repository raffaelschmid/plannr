package ch.plannr.service
{
	import mx.controls.Alert;
	import mx.rpc.http.HTTPService;
	import mx.utils.Base64Encoder;

	public class HttpServiceFactory
	{
		[Inject(source="config.rootUrl")]
		public var rootUrl:String;
		
		[Bindable]
		[Inject(source="context.email", bind="true")]
		public var email:String = null;
		
		[Bindable]
		[Inject(source="context.password", bind="true")]
		public var password:String = null;
		
		
		public function getService(url:String,method:String="GET",authentication:Boolean=false,contentType:String="application/xml"):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method=method;
			service.contentType=contentType;
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			
			if(authentication) 
				service.headers = getAuthorizationHeaders();
			
			return service;
		}
		
		private function getAuthorizationHeaders():Array
		{
			var headers:Array = new Array();
			var encoder:Base64Encoder=new Base64Encoder();
			encoder.encode(this.email + ":" + this.password);
			headers["Authorization"]="Basic " + encoder.toString();
			return headers;
		}
	}
}