package io.netty.example.myhttp.server;

import java.util.Random;

/**
 * <p>
 * Copyright: (C), 2023-11-06 17:02
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RespConstant {
	
	private static final String[] NEWS = {
			"她那时候还太年轻, 不知道所有命运赠送的礼物, 早已在暗中标好了价格. -斯蒂芬·茨威格<<断头皇后>>",
			"这是一个最好的时代, 这是一个最坏的时代; 这是一个智慧的时代, 这是一个愚蠢的时代:\n" +
			"这是希望之春, 这是失望之冬; 人们面前应有尽有, 人们面前一无所有: \n" +
			"人们正踏上天堂之路, 人们正走向地狱之门. -狄更斯<<双城记>>",
	};
	
	private static final Random RANDOM = new Random();
	
	public static String getNews() {
		return NEWS[RANDOM.nextInt(NEWS.length)];
	}
}
