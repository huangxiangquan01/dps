<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
</head>
<style>
    @page {
        @bottom-center {
            content: counter(pages) " - " counter(page);
        }
    }
    body { margin: 0 }
    .sheet {
        background: white;
        box-shadow: 0 .5mm 2mm rgba(0,0,0,.3);
    }
    .pdf-wrapper {
        font-size: 14px;
    }
    table {
        width: 100%;
        th,td {
            text-align: left;
        }
        .label-text {
            font-weight: bold;
        }
    }

</style>
<body class="A4 landscape">
<div class="pdf-wrapper sheet padding-10mm" style="font-size: 14px;;
        overflow: hidden;
        position: relative;
        box-sizing: border-box;
        page-break-before: always;">
    <div>
        <img src="1.png" style="height: 50px; width: 165px; position: absolute; left: 10%">
        <h1 class="title" style="text-align: center;">采购订单</h1>
    </div>
    <table>
        <tbody>
            <tr>
                <td>
                    <label class="label-text">采购订单号:</label>
                    <span>${cpoid}</span>
                </td>
                <td>
                </td>
                <td>
                    <label class="label-text">下单日期:</label>
                    <span>${createtime}</span>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="label-text">采购方:</label>
                    <span>${purchasecom}</span>
                </td>
                <td>
                    <label class="label-text">供货方:</label>
                    <span>${supplyCode}</span>
                </td>
                <td>
                    <label class="label-text">采购员:</label>
                    <span>${purchase}</span>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="label-text">收货方:</label>
                    <span>${receivePerson}</span>
                </td>
                <td>
                    <label class="label-text">地址:</label>
                    <span>${supplyAddress}</span>
                </td>
                <td>
                    <label class="label-text">打印日期:</label>
                    <span>${printtime}</span>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <label class="label-text">联系人:</label>
                    <span>${purchaseLink}</span>
                </td>
                <td>
                    <label class="label-text">联系人:</label>
                    <span>${supplyLink}</span>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <label class="label-text">联系电话:</label>
                    <span>${linkPhone}</span>
                </td>
                <td>
                    <label class="label-text">联系电话:</label>
                    <span>${supplyLinkphone}</span></td>
                <td>
                    <label class="label-text">税率:</label>
                    <span>${rate}</span>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <label class="label-text">公司电话:</label>
                    <span>${companytel}</span>
                </td>
                <td></td>
                <td>
                    <label class="label-text">币种:</label>
                    <span>${exchName}</span>
                </td>
            </tr>
        </tbody>
    </table>
    <table border="1" cellspacing="0" cellpadding="0" style="border-bottom: none">
        <tr>
            <td style="width: 100px">交货日期: ${deliveryTime}</td>
        </tr>
    </table>
    <table border="1" cellspacing="0" cellpadding="0" style="text-align: center;border-bottom: none; border-top: none">
        <thead>
        <tr>
            <th>NO</th>
            <th>平台店铺</th>
            <th>图片</th>
            <th>目的国</th>
            <th>用友SKU</th>
            <th>用友SKU辅助码/老SKU</th>
            <th>贴标SKU</th>
            <th>中文描述</th>
            <th>件重尺</th>
            <th>装箱数</th>
            <th>单价</th>
            <th>数量</th>
            <th>金额</th>
            <th>子PO</th>
        </tr>
        </thead>
        <tbody>
        <#list details! as t>
            <tr>
                <td>${t_index+1}</td>
                <td>
                    <div>${t.platform}</div>
                    <div>${t.store}</div>
                </td>
                <td>
                    <#if t.cinvcodeurl??>
                        <img style="width: 100px" src="${t.cinvcodeurl}" alt=""/>
                    <#else>
                        <div></div>
                    </#if>
                </td>
                <td>${t.siteName}</td>
                <td>${t.cinvcode}</td>
                <td>${t.oldsku}</td>
                <td>随批次甲方发放</td>
                <td>
                    <table style="page-break-inside: avoid">
                        <tr>
                            <td style="width: 220px;">${t.cinvName}</td>
                        </tr>
                    </table>

                </td>
                <td>
                    <div>${t.guige}</div>
                    <div>${t.zxweight}</div>
                </td>
                <td>${t.boxnum}</td>
                <td>${t.iunitprice}</td>
                <td>${t.qty}</td>
                <td>${t.imoney}</td>
                <td>
                    <div>${t.zpo}</div>
                    <div>${t.adjustMemo}</div>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <table border="1" cellspacing="0" cellpadding="0" style="border-top: none; page-break-inside: avoid">
        <tr>
            <td class="td-bold" scope="row" colspan="8">
                <label class="label-text">合计体积:</label>
                <span>${totalVolume}</span>
            </td>
            <td class="td-bold" scope="row" colspan="6">
                <label class="label-text">合计重量:</label>
                <span>${totalWeight}</span>
            </td>
        </tr>
        <tr>
            <td class="td-bold" scope="row" colspan="8">
                <label class="label-text">合计金额(小写):</label>
                <span>${totalAmountMin}</span>
            </td>
            <td class="td-bold" scope="row" colspan="6">
                <label class="label-text">合计金额(大写):</label>
                <span>${totalAmountMax}</span>
            </td>
        </tr>
        <tr>
            <td class="td-bold" colspan="14">
                <label class="label-text">合计下单数量:</label>
                <span>${totalSKUQty}</span>
            </td>
        </tr>
        <tr>
            <td class="td-bold" colspan="14">
                <label class="label-text">注意事项:</label>
                <span>${memo}</span>
            </td>
        </tr>
        <tr>
            <td class="td-bold" colspan="14">
                <label class="label-text">合同有效性:</label>
                <span>${changeMemo}</span>
            </td>
        </tr>
        <tr>
            <td colspan="14">${remark}</td>
        </tr>
    </table>
    <table style="page-break-inside: avoid;font-weight: bold">
        <tbody>
            <tr>
                <td>
                    <div style="width: 30px;"></div>
                </td>
                <td>
                    <div><label class="label-text">采购方代表(签字盖章):</label></div>
                    <div style="margin-top: 6px;"><label class="label-text">签署日期:</label></div>
                </td>
                <td>
                    <div><label class="label-text">供货方代表(签字盖章):</label></div>
                    <div style="margin-top: 6px;"><label class="label-text">${signdate}</label></div>
                </td>
            </tr>
        </tbody>
    </table>
    <table style="page-break-inside: avoid">
        <tr>
            <td>
                <span>${remarkText}</span>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
