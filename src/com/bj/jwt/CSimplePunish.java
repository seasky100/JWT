package com.bj.jwt;

public class CSimplePunish extends CPunish
{
	public void CSimplePunish()
	{
		
	}
	public String printpunish()
	{
		 String m_strPunishInfoPrintTake;
		 String strTellInfo="���з�ʽ���߸�:\n    ����ֱ���������15���ڵ������й������н��ɷ���.\n    ����15��δ���ɵ�,�����ؽ����������Ӵ��������,ÿ�հ����������3%�Ӵ�����,��ɵ���������ȡ�Ӵ����������;����Ҫ�Ӵ�����������,Ҳ�ɳֱ�������ֱ�ӵ����н���.";
		 m_strPunishInfoPrintTake="\n     ���:��110111110001��\n"+
		 "\n��������:"+this.sname+
		 "\n�������:"+this.sarchivesnum+
		 "\n��ʻ֤����:"+this.speopletype+
		 "\n��ʻ֤��:"+this.sidentitycard+
		 "\n��֤����:"+this.slicence+
		 "\n��������"+this.scartype+
		 "\n���ƺ���:"+this.scarnum+
		 "\n    ����"+this.spunishdate+"��"+this.sroadname+"ʵʩ"+this.slawinfo+"Υ����Ϊ,����:"+
		 this.slawarticles+"�Ĺ涨,�������㴦�Է���"+this.slawmoney+"Ԫ�Ĵ���.����<<��������ʻ֤�����ʹ�ù涨>>,��"+this.slawscore+"��."+
		 strTellInfo+
		 "\n    �粻��������,�����յ���������֮������ʮ������"+this.steam+"����"+this.sgoverment+"������������;��������������������Ժ������������.\n\n\n"+
		 "\nִ����:"+this.spolicename+
		 "\n��Ա���:"+this.spolicenum+
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
