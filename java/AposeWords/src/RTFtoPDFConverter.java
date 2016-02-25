import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;

public class RTFtoPDFConverter {

	public static void main(String[] args) {
		
		try{
			Document doc = new Document("C://temp/RTFConvTest/Example2-Logo-simple.rtf");
			PdfSaveOptions pdfops = new PdfSaveOptions();
			doc.save("C:/temp/RTFConvTest/Example2-Logo-Apose.pdf", SaveFormat.PDF);
			
			String hex = "010009000003381e000000000f1e000000000400000003010800050000000b0200000000050000000c026c001c01030000001e00040000000701040004000000070104000f1e0000410b2000cc006d001801000000006b001b010000000028000000180100006d0000000100040000000000000000000000000000000000000000000000000000000000ffffff00c0c0c000808080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111122111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111211111111111111111111111111111111111111111111111111111111111122131111111111111113000311123333333312333332111233311113333333331113332120031111111112210003111333333333111112233311113333333332111133333111211233000030000000130000031111100000111130002110003111200000000000001111111113000011130000000022000211111311110000011100000000010000211200313332211111111111110003300111300000003100000002113021111100310001112002200220311111111300313001110031100211111022021111100312002111111300111231113000110003330021300330021111300111100023031103111112001100333300111111110003230211300333303120211111331111100011111003333001100211130311330211111111111110001111301110311110012031110021101111110011302111303120111201111111130311130211301112011111102101111113021100111111110211130111100211302111102120211130211120311130311110113311111203110011111021111110001111201110311113011011111103111113021111130111110210011110011112221111111111112001111110311031111101103111100110111111001120211100113311110311111113011111031130211231111130210211111001110011111111011110011110011130211112313021111001112031110011111101331111130211001111132111112001111112312031111101101111120311111302111113011111331001113031111111111111111111003111111101103111113110311112031321111203112021130311321111031111111031111100113021123111110021231111100111001111111101111002111001113021111101302111120211203113031111111110111110011100111111311111003111111131103111113110111110031111130211111301111113100111001111111111111111111100211111111110311111211031111100123111130211202110011131111100111111102111110011301112311113002120111120311100111111110111300311100111302111112130211111001120311001111111111011111001110011111121111100211111111120311111111011112003111113021111130111111110011203111111111111111111130011111111111031111111103111113011011110011120212031111111120011111113111111001130111231111003211011113021110011111111011130001110011130211111113021111100112031100111111111102111203111001111111111120011111111111031111111101111000311111302111113011111111001100111111111111111111113031111111111103111121110311111302100000001113021001111111110031111111211111200113021123111200321130000001111001111111101110020111001113011112111302111113011203120011111111112000000211100111111111113031111111111203111121110111100131111130211111301111211100120011111111111111111111003111111111110311122111031111120313211203111202100111111113001111111111111100011302112311100232113311100111100111111110111001021100111302111311130211111302120311031111111111101110011110011111111111003111111111110311123111011130313111113021111100111131110010021111111111111111111100211111111111031113211103111112031331120211120300211111113003111111111111130031130111231120013211231110311110011111111011300133110011130111101113021111130212031303111111111110111001111001111111111100311111111112031113311101110011311111302111113011110111003001111111111111111111110021111111111100330021110311111203123113011112000001111112000111331111111130001113011123110031321110113021111001111111101100312011001113000000111302111112021203130311111111111011203111100111111111110031111111111100000031110113031231111130211111300000311100000011111111111111111111002111111111110033302112031111120311011001111302120011111000111130021111130003111301112312001132111011301111100111111110110021101100111303333011130211111302120312031111111111132130211110011111111111003111111111120022303111011001133111113021111130222301110011300111111111111111111100211111111111031113211203111113031101103111120211203111000211113000311120000111130111231303113211132100111110011111111012001110110011100111101113021111130212031203111111111112313011111001111111111100311111111111031112211101303113211111302111110011113111001113021111111111111111110021111111111103111221120311111302113220211112021110011100211111130001110000111113011123100111321112310311111001111111101303111331001110011113111302111110011203120011111111111101001111100111111111110031111111111203111221110100211321111130211111001111311100111203111111111111111111002111111111110311111112031111100111233011111202111001120011112111130113000111111301112330311132111102021111100111111110100311120100111001111211130211111001120311001111111311110103111110011111111111003111111111110311111111020011132111113021111130111111110011120311111111111111111100311111111111031111111203111110011110001111130211100112021112211111111000111111130111230021113211110301111110011111111010011111010011100111111113021111203112031100111111131111300211111001111111111100311111111112031111111100021113212111302112210011111111001112031111111111111111113031111111121103111122120311113031111003111112021110011201111321111111100211112113011120001111321111300111111001111111102001111101001110011111311302111100211203112031111133111120011111100111111111113031111111131103111122110001111331321130211321001111131100111203111111111111111111300111111123110311113212031111001111130311111202111031110111101111111120011111311301111002111102111130311111100111111110303111113200111001111131130211130311120311100111110311111001111110011111111111200111111110110311113211003111133123113021102100111110110011100111111111111111111110011111112312031111021203111002111112011111130211002111031130111111113031111231100111300111110311112021111110011111111000311111230011130111120113021130011113031112031113031111103111111001111111111110011111112312031111021200111113312021302120210011113311001130311111111111111111111002111111331000000002100000002111111101111120000003111120330011111111202111133130031200211113002111101111120003111111100011111110001130000000013000000311113000311130030003111110211111000011111111111302111111032000000002200311113003200000000020000000031000000311111111111111111111120311111103233333333123333321111111113111113333321111111203121111111110311110213333222211111222211112111112222211111110001111111000112222222221222222111111222221111133311211111211111122221111111111110011111103222222222122211111222212222222221222222221122222111111111111111111111111002111100311111111111111111111111111111111111111111111111111111111111001113021111111111111111111111111111111111111111003111111130021111111111111111111111111111111111111111111111111111111111111111111303111100311111111111111111111111111111111111111111111111111111111111111111111111110031200021111111111111111111111111111111111111111111111111111111111120323002111111111111111111111111111111111111111300311111112000111111111111111111111111111111111111111111111111111111111111111111113002300031111111111111111111111111111111111111111111111111111111111111111111111111100000232111111111111111111111111111111111111111111111111111111111111300023111111111111111111111111111111111111111333311111111133331111111111111111111111111111111111111111111111111111111111111111111130003123111111111111111111111111111111111111111111111111111111111111111111111111111122111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111222332221111111111111111112333332211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111230000000000003211111111113000000000000032111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111113000000000000000000311111130000000000000000003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130000000003333000000000311000000000033330000000003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111200000032111111111130000000000000321111211111300000001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111300000311230000000032113000000002112300000000321130000021111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000003113000000000000003110000031230000000000000031100000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000001130000000000000000003120011000000000000000000031300003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000031200000000000000000000001212000000000000000000000012000021111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111300031300000000000000000000000311000000000000000000000000110000211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111300031300000000330000000000000310110000000000000033000000001100001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100001300000311111112000000000013001200000001002111111300000012000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130001200003111133003212000000012000013000003111100003111000000130001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110003100002111300000000210000031000003100000311200000001120000031000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111113000130002111000000000003100001300200013000031200000000211300000130001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000310003111300000000000033002200213003100003100000000031120000031000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111200013000111200000000000000000130001230013000130000000003111000000130001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130001000311100000000000000000310000231003100010000000000311100000022000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110003200011110000000000000000032000311000010003000000000011110000003100021111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000230001112000000000000000001300111000001000000000000021113000000010003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100013000111300000000000000000100010100000130000000000011111000000001300311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130001000311130000000000000000010003110000022000000000311111300000000130001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111113000100031113000000000000000001000001130002200000000211111300000000023003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111300010003111300000000000000000100000101300220000003111111300000000002300311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130001000311130000000000000000010000010130023000002111112000000000000130031111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112000100031113000000000000000001000001130001300002111113000000000000013003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100013000111300000000000000000130021100000100003111120000000000000001000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110002200011120000000000000000022031010000010000211130000000000000000100021111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000310002111000000000000002003103101021031000011120000000020000000220001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130001300011100000000000000100010021112001300001110000000001000000013000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000220002112000000000000310002200010000100000112000000003100000031000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100001000011130000000000011000010001200120000011200000000110000001300011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112000210000111300000000011100002200130310000003110000000211000003100031111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111100001300001112000000311110000013030013000000011200000211300000130001111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112000310000031112332111211000003100012000000000211222113120000120003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130003100000032111220000200000021021000000000003211230033000110000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111000021000000000000000000000000211000000000000000000000000110000211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000213000000000000000000000011100000000000000000000000110000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111200003120000000000000000000312031200000000000000000003120000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112000001120000000000000003113000011300000000000000031130000311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111120000031123000000000321130000000311230000000003211300000211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111130000002111111111112300000000000021111111111123000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111200000000033333300000000312000000000333330000000003111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111200000000000000000003111113000000000000000000031111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111230000000000000031111111112300000000000000211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112330003322111111111111111123333333211111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111040000002701ffff030000000000";		

			try {
				RTFtoPDFConverter test = new RTFtoPDFConverter();
				byte[] imageBytes = test.convertHexToByte(hex);
				
				Object localObject = new asposewobfuscated.zzMM(imageBytes);
				
				if (asposewobfuscated.zzPO.zzv(imageBytes))
				{
				  int picw = 7486;	 
				  int pich = 2831;
				  localObject = asposewobfuscated.zzPP.zzZ(asposewobfuscated.zzWQ.zzA( picw / 100.0D), asposewobfuscated.zzWQ.zzA(pich / 100.0D), 96.0D, 96.0D);
				  imageBytes = asposewobfuscated.zzPO.zzZ(imageBytes, (asposewobfuscated.zzPP)localObject);
				}
				
				InputStream in = new ByteArrayInputStream(imageBytes);
				BufferedImage bImageFromConvert = ImageIO.read(in);
				ImageIO.write(bImageFromConvert, "bmp", new File("c:/temp/Hex.bmp"));
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public byte[] convertHexToByte(String img) {
		byte[] b = new byte[img.length() / 2];
		int i;
		try {
			for (i = 0; i <= img.length() / 2 - 1; i++) {
				b[i] = (byte) Integer.parseInt(img.substring(2 * i, 2 * i + 2), 16);
			}
			return b;
		} catch (Exception ex) {
			ex.printStackTrace();
			return b;
		}
	}

}
