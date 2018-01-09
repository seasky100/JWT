package com.bj.jwt;

public class CSimplePunish extends CPunish
{
	public void CSimplePunish()
	{
		
	}
	public String printpunish()
	{
		 String m_strPunishInfoPrintTake;
		 String strTellInfo="履行方式及催告:\n    你须持本决定书在15日内到北京市工商银行缴纳罚款.\n    超过15日未缴纳的,本机关将依法作出加处罚款决定,每日按罚款数额的3%加处罚款,你可到本机关领取加处罚款决定书;不需要加处罚款决定书的,也可持本决定书直接到银行缴纳.";
		 m_strPunishInfoPrintTake="\n     编号:第110111110001号\n"+
		 "\n被处罚人:"+this.sname+
		 "\n档案编号:"+this.sarchivesnum+
		 "\n驾驶证种类:"+this.speopletype+
		 "\n驾驶证号:"+this.sidentitycard+
		 "\n发证机关:"+this.slicence+
		 "\n车辆类型"+this.scartype+
		 "\n车牌号码:"+this.scarnum+
		 "\n    你于"+this.spunishdate+"在"+this.sroadname+"实施"+this.slawinfo+"违法行为,根据:"+
		 this.slawarticles+"的规定,决定对你处以罚款"+this.slawmoney+"元的处罚.根据<<机动车驾驶证申领和使用规定>>,记"+this.slawscore+"分."+
		 strTellInfo+
		 "\n    如不服本决定,可在收到本决定书之日起六十日内向"+this.steam+"或者"+this.sgoverment+"申请行政复议;或者在三个月内向人民法院提起行政诉讼.\n\n\n"+
		 "\n执勤民警:"+this.spolicename+
		 "\n警员编号:"+this.spolicenum+
		 "\n"+this.spunishdate+
		 "\n";	
		return m_strPunishInfoPrintTake;
	}
	public void SavepunishDB()
	{
		
	}
	public boolean UploadpunishDB()
	{
		
		return true;
	}
}
