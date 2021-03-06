import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemSkuDeleteRequest;
import com.taobao.api.response.ItemSkuDeleteResponse;


public class TbSkuDelete {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
				"12491455", "2ff36d14aab55e0f76cfe10c2703ce50");
		List<Map> l = new ArrayList();
		l.add(WebUtil.toMap("numiid",12463963817L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",10797630294L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",13162876156L,"prop","1627207:28320;21921:28394"));
		l.add(WebUtil.toMap("numiid",13162876156L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",13162876156L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",13162876156L,"prop","1627207:28320;21921:44905"));
		l.add(WebUtil.toMap("numiid",13162876156L,"prop","1627207:28320;21921:44906"));
		l.add(WebUtil.toMap("numiid",12981510236L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",12981510236L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",15167347789L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",15242200919L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",12265772372L,"prop","1627207:28320;1626580:28315"));
		l.add(WebUtil.toMap("numiid",13019243511L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",15242260087L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",12382838483L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",12397002200L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",12811006197L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",14162180452L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",13758501162L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",14420581987L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",15167379459L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",12464019441L,"prop","1627207:28320;21921:44902"));
		l.add(WebUtil.toMap("numiid",12464019441L,"prop","1627207:28320;21921:28394"));
		l.add(WebUtil.toMap("numiid",12464019441L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",12464019441L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",12464019441L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",12268668090L,"prop","1627207:28320;1626580:28317"));
		l.add(WebUtil.toMap("numiid",14423173249L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",14423173249L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",14423173249L,"prop","1627207:28320;21921:28392"));
		l.add(WebUtil.toMap("numiid",16784576898L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",16784576898L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",16784576898L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13466844785L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",10798083076L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",10798083076L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",10798083076L,"prop","1627207:28320;20561:3247426"));
		l.add(WebUtil.toMap("numiid",16790016571L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",16790016571L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",16790016571L,"prop","1627207:28320;21921:28390"));
		l.add(WebUtil.toMap("numiid",15416756962L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",15416756962L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",15135684594L,"prop","1627207:28320;5425193:28314"));
		l.add(WebUtil.toMap("numiid",14170687179L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13756690832L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13613693616L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",6891727780L,"prop","1627207:28320;10593286:97048032"));
		l.add(WebUtil.toMap("numiid",13757154830L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",14170647593L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",12676341017L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",12609936299L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",12673426940L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13287578932L,"prop","1627207:28320;21923:28383"));
		l.add(WebUtil.toMap("numiid",13673101564L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13673073941L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",7076964514L,"prop","1627207:28320;20549:28923"));
		l.add(WebUtil.toMap("numiid",12813785316L,"prop","1627207:28320;20561:3247426"));
		l.add(WebUtil.toMap("numiid",14419726193L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",12609828879L,"prop","1627207:28320;10593286:97047979"));
		l.add(WebUtil.toMap("numiid",12609828879L,"prop","1627207:28320;10593286:97047950"));
		l.add(WebUtil.toMap("numiid",13247747692L,"prop","1627207:28320;1626580:28318"));
		l.add(WebUtil.toMap("numiid",13247747692L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",14217495059L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",16788188491L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",15119976542L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13757062809L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",15167407049L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13901288047L,"prop","1627207:28320;10593286:97047950"));
		l.add(WebUtil.toMap("numiid",10801706102L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",14162092552L,"prop","1627207:28320;21921:28392"));
		l.add(WebUtil.toMap("numiid",14090843484L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",16788172909L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",16788172909L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13313551508L,"prop","1627207:28320;10593286:97047910"));
		l.add(WebUtil.toMap("numiid",13313551508L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",14508551393L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",12587916675L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",12587916675L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",13313563396L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",13313563396L,"prop","1627207:28320;10593286:97047910"));
		l.add(WebUtil.toMap("numiid",15170823921L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",15170823921L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",14284479135L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",14284483042L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",12887178242L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",14284387809L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",14170663478L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13023185930L,"prop","1627207:28320;10593286:97048032"));
		l.add(WebUtil.toMap("numiid",7061555464L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",9987062928L,"prop","1627207:28320;1626580:28317"));
		l.add(WebUtil.toMap("numiid",9987062928L,"prop","1627207:28320;1626580:28318"));
		l.add(WebUtil.toMap("numiid",9987062928L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",9987062928L,"prop","1627207:28320;1626580:28315"));
		l.add(WebUtil.toMap("numiid",12587940425L,"prop","1627207:28320;21921:28394"));
		l.add(WebUtil.toMap("numiid",12587940425L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",12809810484L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",12809810484L,"prop","1627207:28320;1626580:28318"));
		l.add(WebUtil.toMap("numiid",13909472555L,"prop","1627207:28320;1626580:28319"));
		l.add(WebUtil.toMap("numiid",14284415567L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",13090218239L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",13090218239L,"prop","1627207:28320;21921:29542"));
		l.add(WebUtil.toMap("numiid",13090218239L,"prop","1627207:28320;21921:28388"));
		l.add(WebUtil.toMap("numiid",13757230068L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",15242260068L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",15539936087L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",9986937730L,"prop","1627207:28320;1626580:28317"));
		l.add(WebUtil.toMap("numiid",14420605524L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",14420605524L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13271823755L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",13271823755L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",15167299783L,"prop","1627207:28320;21921:28390"));
		l.add(WebUtil.toMap("numiid",15167299783L,"prop","1627207:28320;21921:28391"));
		l.add(WebUtil.toMap("numiid",15167299783L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",13757106164L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",12397022399L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",12397022399L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",12397022399L,"prop","1627207:28320;21921:44902"));
		l.add(WebUtil.toMap("numiid",12742939156L,"prop","1627207:28320;21923:28314"));
		l.add(WebUtil.toMap("numiid",14363907852L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",14170651537L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13672290473L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",14408689118L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",15135684599L,"prop","1627207:28320;5425193:28314"));
		l.add(WebUtil.toMap("numiid",13313539745L,"prop","1627207:28320;10593286:97047910"));
		l.add(WebUtil.toMap("numiid",13313539745L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",9987060272L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",9987060272L,"prop","1627207:28320;1626580:28315"));
		l.add(WebUtil.toMap("numiid",13757126074L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",16784728045L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",16784728045L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",13758593695L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",13816818767L,"prop","1627207:28320;21921:44901"));
		l.add(WebUtil.toMap("numiid",12386442695L,"prop","1627207:28320;21921:28394"));
		l.add(WebUtil.toMap("numiid",12386442695L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",13613713325L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",13757062673L,"prop","1627207:28320;10593286:97048032"));
		l.add(WebUtil.toMap("numiid",14420637129L,"prop","1627207:28320;21921:28391"));
		l.add(WebUtil.toMap("numiid",14420637129L,"prop","1627207:28320;21921:28390"));
		l.add(WebUtil.toMap("numiid",14420637129L,"prop","1627207:28320;21921:28389"));
		l.add(WebUtil.toMap("numiid",12574400477L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",15703072947L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",15416780450L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",10797632726L,"prop","1627207:28320;20561:3236869"));
		l.add(WebUtil.toMap("numiid",14162236458L,"prop","1627207:28320;10593286:97047979"));
		l.add(WebUtil.toMap("numiid",14284479274L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",13673121257L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",16745844480L,"prop","1627207:28320;1626580:28317"));
		l.add(WebUtil.toMap("numiid",12609900123L,"prop","1627207:28320;10593286:97047792"));
		l.add(WebUtil.toMap("numiid",12664814394L,"prop","1627207:28320;21921:28395"));
		l.add(WebUtil.toMap("numiid",12664814394L,"prop","1627207:28320;21921:44906"));
		l.add(WebUtil.toMap("numiid",12664814394L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",12664814394L,"prop","1627207:28320;21921:44905"));
		l.add(WebUtil.toMap("numiid",15169763276L,"prop","1627207:28320;21921:44898"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28313"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28318"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28317"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",15135624969L,"prop","1627207:28320;1626580:28315"));
		l.add(WebUtil.toMap("numiid",10963830448L,"prop","1627207:28320;21921:28393"));
		l.add(WebUtil.toMap("numiid",12397785995L,"prop","1627207:28320;1626580:28314"));
		l.add(WebUtil.toMap("numiid",14490322181L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",14529558434L,"prop","1627207:28320;10593286:97047950"));
		l.add(WebUtil.toMap("numiid",14529570198L,"prop","1627207:28320;10593286:97047979"));
		l.add(WebUtil.toMap("numiid",14529614481L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",14529614651L,"prop","1627207:28320;20549:28931"));
		l.add(WebUtil.toMap("numiid",14529630621L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",14532925536L,"prop","1627207:28320;21921:28391"));
		l.add(WebUtil.toMap("numiid",14532981478L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",14532989066L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",14533013273L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",15315751439L,"prop","1627207:28320;1626580:28316"));
		l.add(WebUtil.toMap("numiid",15315767755L,"prop","1627207:28320;20561:3227230"));
		l.add(WebUtil.toMap("numiid",15315783051L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",17014980976L,"prop","1627207:28320;10593286:97047950"));
		l.add(WebUtil.toMap("numiid",17015060794L,"prop","1627207:28320;20561:3264519"));
		l.add(WebUtil.toMap("numiid",17015080597L,"prop","1627207:28320;10593286:97047979"));
		l.add(WebUtil.toMap("numiid",17015152112L,"prop","1627207:28320;10593286:97047979"));
		l.add(WebUtil.toMap("numiid",17015232091L,"prop","1627207:28320;20561:3227230"));
		l.add(WebUtil.toMap("numiid",17457012573L,"prop","1627207:28320;21921:30106"));
		l.add(WebUtil.toMap("numiid",17457012573L,"prop","1627207:28320;21921:671"));
		l.add(WebUtil.toMap("numiid",17457012573L,"prop","1627207:28320;21921:44896"));
		l.add(WebUtil.toMap("numiid",17457012573L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:671"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:29542"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:44896"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:28388"));
		l.add(WebUtil.toMap("numiid",17481276227L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:671"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:29542"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:44896"));
		l.add(WebUtil.toMap("numiid",15616967302L,"prop","1627207:28320;21921:28388"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:671"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:29542"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:44896"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:28388"));
		l.add(WebUtil.toMap("numiid",15617243578L,"prop","1627207:28320;21921:672"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:670"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:671"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:29542"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:44896"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:28388"));
		l.add(WebUtil.toMap("numiid",17481292215L,"prop","1627207:28320;21921:672"));
		ItemSkuDeleteRequest isd = new ItemSkuDeleteRequest();
		try {
			for(Map m:l)
			{
				isd.setNumIid((Long)m.get("numiid"));
				isd.setProperties((String)m.get("prop"));
				ItemSkuDeleteResponse r = client.execute(isd, "61014275809dfb98f533c0d13b9000c4b12189309bc49f6446338500");
				System.out.println(m.get("numiid")+":"+r.getMsg()+","+r.getSubMsg());
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
