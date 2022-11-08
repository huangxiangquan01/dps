<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title></title>
    <style>
      @page{
        size:a4;
        margin-top: 130px;
      }
    </style>
</head>
<body >
<#--<div class="beginning" style="width: 654px;  padding: 0px; margin: 0px auto; font-family:SimSun; font-size: 14px;">
    <div class="beginning-time" style="text-align: center;margin-bottom: 10px;">
				<span class="beginning-time-one" style="width: 219px;margin-right: 40px;line-height: 32px; ">
					<span>就诊时间：</span>
					<span>2022-10-17 08:00xx</span>
				</span>
        <span class="beginning-time-one" style="width: 219px;margin-right: 40px;line-height: 32px; ">
					<span>打印时间：</span>
				<span>${createDate}</span>
			</span>
    </div>
    <div class="beginning-pat" style="display: flex;align-items: center;justify-content: space-between;height: 60px;border-top: 1px solid rgba(0, 0, 0, 0.16);border-bottom: 1px solid rgba(0, 0, 0, 0.16);">
				<span>
					<span>姓名：</span>
					<span>${name}</span>
				</span>
        <span>
					<span>性别：</span>
					<span>${gender}</span>
				</span>
        <span>
					<span>年龄：</span>
					<span>${age}</span>
				</span>
        <span>
					<span>登记号：</span>
					<span>${patientId}</span>
				</span>
        <span>
					<span>科室：</span>
					<span>泛血管中心（南区）</span>
				</span>
    </div>
</div>-->
<div class="module module-text" style="width: 654px;  padding: 0px; margin-top: 10px;border-bottom: 1px solid rgba(0, 0, 0, 0.16); font-family:SimSun;">
    <div class="head">
        综合门诊诊疗意见
    </div>
    <div class="main">
        <div class="main-strip" style="font-size: 14px;display: flex;margin: 20px 0;">
            <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">初次诊断：</span>
            <span class="content">西医诊断：1.高血压病  2.房性期外收缩（房性早搏）</span>
        </div>
        <div class="main-strip" style="font-size: 14px;display: flex;margin: 20px 0;">
            <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">本次医嘱：</span>
            <span class="content">1.琥珀酸美托洛尔缓释片（锡阿斯利康-47.5mgX7片(房性早搏）2.门诊随访，必要时再次复查动态心电图</span>
        </div>
        <div class="main-strip" style="font-size: 14px;display: flex;margin: 20px 0;">
            <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">告知：</span>
            <span class="content">用药前请参阅说明书，如有疑问请咨询医生或用药咨询门诊钥匙（门诊一楼）定期复查随访，有情况随时就诊；吸烟有害健康，请勿吸烟。</span>
        </div>
        <div class="main-strip" style="font-size: 14px;display: flex;margin: 20px 0;">
            <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">随访计划：</span>
            <span class="content">这一段是随访计划这一段是随访计划这一段是随访计划这一段是随访计划这一段是随访计划这一段是随访计划这一段是随访计划这一段是随访计划。</span>
        </div>
    </div>
    <div class="footer" style="font-size: 14px;display: flex;justify-content: flex-end;margin-bottom: 20px;">
				<span class="footer-item" style="margin-right: 20px;">
					<span>门诊医生：</span>
					<span>亓慧</span>
				</span>
        <span class="footer-item" style="margin-right: 20px;">
					<span>日期：</span>
					<span>2019-10-03 10:33</span>
				</span>
    </div>
</div>
<div class="module module-text" style="width: 654px;  padding: 0px; margin-top: 10px;border-bottom: 1px solid rgba(0, 0, 0, 0.16); font-family:SimSun;">
    <div class="head">
        检查检验阳性指标
    </div>

    <div class="footer" style="font-size: 14px;display: flex;justify-content: flex-end;margin-bottom: 20px;">
				<span class="footer-item" style="margin-right: 20px;">
					<span>日期：</span>
					<span>2019-10-03 10:33</span>
				</span>
    </div>
</div>

<#if testViewList?exists>
    <#list testViewList as testView>
<div class="module module-examination" style="width: 654px;  padding: 0px; margin-top: 10px;border-bottom: 1px solid rgba(0, 0, 0, 0.16); font-family:SimSun;">
    <div class="head">
        ${testView.testName}
    </div>
    <div class="main">
        <table style="font-size: 14px;border-collapse: collapse;line-height: 40px;min-width: 100%;text-align: left;margin: 20px 0;">
            <tr class="table-header" style="background: #ECEDF0;box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">
                <th style="padding-left: 10px;">缩写</th>
                <th style="padding-left: 10px;">项目名称</th>
                <th style="padding-left: 10px;">结果</th>
                <th style="padding-left: 10px;">单位</th>
                <th style="padding-left: 10px;">参考值</th>
            </tr>
            <tr></tr>
            <#list testView.testDetailViews as testDetail>
            <tr>
                <td style="padding-left: 10px; box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">${testDetail.chkRptCode}</td>
                <td style="padding-left: 10px; box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">${testDetail.chkRptName}</td>
                <td style="padding-left: 10px; box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">
                    <span>${testDetail.chkRsVal}</span>
                </td>
                <td style="padding-left: 10px; box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">${testDetail.chkRsUnit}</td>
                <td style="padding-left: 10px; box-shadow: inset 0px -1px 0px 0px rgba(15, 30, 77, 0.08);">${testDetail.chkNormalVal}</td>
            </tr>
            </#list>
        </table>
    </div>
    <div class="footer" style="font-size: 14px;display: flex;justify-content: flex-end;margin-bottom: 20px;">
				<span class="footer-item" style="margin-right: 20px;">
					<span>门诊医生：</span>
					<span>${testView.appDocName}</span>
				</span>
        <span class="footer-item" style="margin-right: 20px;">
					<span>审核医师：</span>
					<span>${testView.auditDocName}</span>
				</span>
        <span class="footer-item" style="margin-right: 20px;">
					<span>日期：</span>
					<span>${testView.reportDate}</span>
				</span>
    </div>
</div>
    </#list>
</#if>
<div class="module module-inspect" style="width: 654px;  padding: 0px; margin-top: 10px;border-bottom: 1px solid rgba(0, 0, 0, 0.16); font-family:SimSun;">
    <div class="head">
        十二通道常规心电图
    </div>
    <div class="main">
        <div class="main-detail">
            <div class="main-detail-strip">
                <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">心电图：</span>
                <span class="content">
							<span class="content-item">
								<span>PR：</span>
								<span>172ms</span>
							</span>
							<span  class="content-item">
								<span>心室率：</span>
								<span>76bmp</span>
							</span>
							<span  class="content-item">
								<span>心室率：</span>
								<span>76bmp</span>
							</span>
						</span>
            </div>
            <div class="main-detail-strip">
                <span class="topic" style="font-weight: bold;margin-right: 40px;flex-shrink: 0;line-height: 20px;width: 140px;text-align: right; ">诊断结论：</span>
                <span class="content">1.窦性心律2.T波改变</span>
            </div>
        </div>
        <div class="main-pic" style="text-align: center;">
            <img class="inspect-pic" src="${imgUrl}" id="abi" style="width:400px" alt=""></img>
        </div>
        <div class="footer" style="font-size: 14px;display: flex;justify-content: flex-end;margin-bottom: 20px;">
					<span class="footer-item" style="margin-right: 20px;">
						<span>报告医师：</span>
						<span>亓慧</span>
					</span>
            <span class="footer-item" style="margin-right: 20px;">
						<span>日期：</span>
						<span>2019-10-03 10:35</span>
					</span>
        </div>
    </div>
</div>
</body>
</html>