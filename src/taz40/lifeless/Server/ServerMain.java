package taz40.lifeless.Server;

public class ServerMain {

	public static void main(String[] args){
		if(args.length == 1){
			new ServerMain(Integer.parseInt(args[0]));
		}else{
			new ServerMain(1337);
		}
	}
	
	public ServerMain(int port){
		
	}
	
}
