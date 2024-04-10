import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;



public class Main extends JFrame {


	private JPanel MainForm;

	private JTabbedPane tabPanel;
	private JPanel clockPanel;
	private JPanel timerPanel;
	private JPanel stopwatchPanel;
	private JLabel clock;
	private JButton startButton;
	private JComboBox<String> cbHours;
	private JComboBox<String> cbMinutes;
	private JComboBox<String> cbSeconds;
	private JLabel countdownTimer;
	private JButton stopWatchStartStopBtn;
	private JLabel stopWatchLbl;
	private JButton pauseButton;
	private JButton timeSplitButton;
	private JTextArea timeSplitArea;
	private Countdown countdown;
	private StopWatch stopWatch;



	Thread clockThread = new Thread(() -> {
		try {
			while(true) {
				Thread.sleep(999);
				clock.setText(java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	});



	public Main(){
		setContentPane(MainForm);
		setTitle("PAGUI APP");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);


		addComboBoxValues();

		clockThread.start();



		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (startButton.getText().equals("Start")){

					new Thread(() -> {
							long time = getTime();
							countdown = new Countdown(time);
							startButton.setText("Stop");

								while (countdown.getInterval() > -1) {
									try {
										long secs = countdown.getInterval();
										long sec = secs % 60;
										long min = secs / 60 % 60;
										long hour = secs / 60 / 60 % 24;

//
										countdownTimer.setText(String.format("%02d",hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
										Thread.sleep(999);
										if (countdown.getInterval() == 0) {
											startButton.setText("Start");

										}
										countdown.countDown();


									} catch (Exception e1) {
										System.out.println(e1);
									}
								}

						}).start();
				}
				else {
					startButton.setText("Start");
					countdownTimer.setText("00:00:00");
					countdown.setInterval(0);

				}

			}

		});


		stopWatchStartStopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				startStopSW();

			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseResumeSW();
			}
		});
		timeSplitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				splitTimeSW();

			}
		});
	}

	public void splitTimeSW(){
		if (timeSplitArea.getRows() >= 13){
			timeSplitArea.setText("");
			timeSplitArea.setRows(0);
		}
		System.out.println(timeSplitArea.getRows());
		timeSplitArea.append(stopWatch.formatTime() + "\n");
		timeSplitArea.setRows(timeSplitArea.getRows()+1);
	}
	public void pauseResumeSW(){
		if (pauseButton.getText().equals("Pause")){
			stopWatch.suspend();
			pauseButton.setText("Resume");
			timeSplitButton.setEnabled(false);
		}
		else{
			stopWatch.resume();
			pauseButton.setText("Pause");
			timeSplitButton.setEnabled(true);
		}
	}
	public void startStopSW(){
		if (stopWatchStartStopBtn.getText().equals("Start")){
			new Thread(() -> {
				stopWatchStartStopBtn.setText("Stop");
				stopWatch = new StopWatch();
				stopWatch.reset();
				stopWatch.start();
				pauseButton.setEnabled(true);
				pauseButton.setText("Pause");
				timeSplitButton.setEnabled(true);
				while (stopWatch.isStarted()){
					stopWatchLbl.setText(stopWatch.formatTime());
				}
			}).start();
		}
		else {
			stopWatchStartStopBtn.setText("Start");
			stopWatch.stop();
			pauseButton.setEnabled(false);
			timeSplitArea.setText("");
			timeSplitButton.setEnabled(false);
			stopWatchLbl.setText("00:00:00.000");
		}
	}
	public int getTime(){
		String hours = String.valueOf(cbHours.getSelectedItem());
		String minutes = String.valueOf(cbMinutes.getSelectedItem());
		String seconds = String.valueOf(cbSeconds.getSelectedItem());
		return Integer.parseInt(hours) * 60 * 60 + Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);

	}

	public void addComboBoxValues(){
		String hour;
		String minSec;
		for (int i = 0; i < 24; i++){
			hour = String.valueOf(i);
			if (hour.length()<2) {
				hour = "0" + hour;
			}
			cbHours.addItem(hour);
		}
		for (int i = 0; i < 60; i++){
			minSec = String.valueOf(i);
			if (minSec.length()<2){
				minSec = "0" + minSec;
			}
			cbMinutes.addItem(minSec);
			cbSeconds.addItem(minSec);
		}
	}

	public static void main(String[] args) {
		new Main();



	}



}
