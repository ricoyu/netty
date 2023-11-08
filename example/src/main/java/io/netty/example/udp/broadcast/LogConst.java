package io.netty.example.udp.broadcast;

import java.util.Random;

/**
 * <p>
 * Copyright: (C), 2023-11-08 15:31
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class LogConst {
	
	public static final int MONITOR_SIDE_PORT = 9998;
	
	private static final String[] LOG_INFS = {
			"20180912:mark-machine:Send sms to 1001",
			"20180912:lison-machine:Send email to james@enjoyedu",
			"20180912:james-machine:Happen Exception",
			"20180912:peter-mahcine:认生不能像做菜, 把所有的料都准备好了才下锅",
			"20180912:deer-machine:牵着你的手, 就像左手牵右手没感觉, 但砍下去也会痛!",
			"20180912:king-machine:我听别人说这世界上有一种鸟事没有脚的, " +
					"它只能一直飞呀飞呀, 飞累了就在风里睡觉, 这种鸟一辈子只能下地一次, " +
					"那一次是它死亡的时候. ",
			"20180912:mark-machine:在我出道的时候, 我认识了一个人, " +
					"因为他喜欢在东边出没, 所以很多年以后, 他有个绰号叫东邪,",
			"20180912:liso-machine: 做人如果没有梦想, 那和咸鱼有什么区别",
			"20180912:james-machine:恐惧让你沦为囚犯, 希望让你重获自由, " +
					"坚强的人只能救赎自己, 伟大的人才能救赎别人. " +
					"记着, 希望是件好东西, 而且从没有一件好东西会消逝. " +
					"忙活, 或者等死",
			"20180912:peter-machine:世界上最远的距离不是生和死, " +
					"而是我站在你面前却不能说: 我爱你",
			"20180912:deer-machine:成功的含义不在于得到什么, " +
					"而是在于你从那个奋斗的起点走了多远.",
			"20180912:king-machine:杀一为罪, 屠万为雄. ",
			"20180912:mark-machine:世界在我掌握中, 我却掌握不住你的感情",
			"20180912:lison-machine:我害怕前面的路, 但是一想到你, 就有能力向前走了.",
			"20180912:james-machine:早就劝你别吸烟, 可是烟雾中你是那么美, "+
					"叫我怎么劝得下口",
			"20180912:peter-machine:如果你只做自己能力范围内的事情, 就永远无法进步. " +
					"昨天的已成历史, 明天是未知的, 而今天是上天赐予我们的礼物, " +
					"这就是为什么我们把它叫做现在!",
			"20180912:deer-machine:年轻的时候又贼心没贼胆, 等到老了吧, " +
					"贼心贼胆都有了, 可贼又没了. ",
			"20180912:king-mahchine:别看现在闹得欢, 小心将来拉清单."};
	
	private final static Random r = new Random();
	
	public static String getLogInfo() {
		return LOG_INFS[r.nextInt(LOG_INFS.length-1)];
	}
}
