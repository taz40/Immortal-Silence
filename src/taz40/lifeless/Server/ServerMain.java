package taz40.lifeless.Server;

public class ServerMain {

	public static void main(String[] args){
		if(args.length == 1){
			new Server(Integer.parseInt(args[0]));
		}else{
			new Server(1337);
		}
	}
	
}
