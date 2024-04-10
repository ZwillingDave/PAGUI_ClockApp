
public class Countdown {

	private long interval = 0;

	public Countdown(long interval){
		this.interval = interval;

	}

	public long getInterval(){
		return interval;
	}
	public void countDown(){
		this.interval = this.interval -1;
	}
	public void setInterval(long interval){
		this.interval = interval;
	}

}

