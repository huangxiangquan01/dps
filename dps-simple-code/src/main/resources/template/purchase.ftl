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
            content: "Page " counter(page) " of " counter(pages-1);
        }
    }
    body { margin: 0 }
    .sheet {
        background: white;
        box-shadow: 0 .5mm 2mm rgba(0,0,0,.3);
        margin: 5mm auto;
    }
    .pdf-wrapper {
        font-size: 14px;
    }
    table {
        width: 100%;
        th,td {
            text-align: left;
        }
    }
</style>
<body class="A4 landscape">
<div class="pdf-wrapper sheet padding-10mm" style="font-size: 14px;;
        overflow: hidden;
        position: relative;
        box-sizing: border-box;
        page-break-after: always;">
    <h1 class="title" style="text-align: center;">采购订单</h1>
    <table>
        <tbody>
            <tr>
                <td>
                    <label class="label-text">采购订单号:</label>
                    <span>${cpoid}</span>
                </td>
                <td>
                    <label class="label-text">供货方:</label>
                    <span>${supplyCode}</span>
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
                    <label class="label-text">地址:</label>
                    <span>${supplyAddress}</span>
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
                    <label class="label-text">联系人:</label>
                    <span>${supplyLink}</span>
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
                    <label class="label-text">联系电话</label>
                    <span>${supplyLinkphone}</span>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <label class="label-text">联系电话</label>
                    <span>${linkPhone}</span>
                </td>
                <td></td>
                <td>
                    <label class="label-text">税率</label>
                    <span>${rate}</span>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <label class="label-text">公司电话</label>
                    <span>${companytel}</span>
                </td>
                <td></td>
                <td>
                    <label class="label-text">币种</label>
                    <span>${exchName}</span>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="table-wrapprt">
        <table border="1" cellspacing="0" cellpadding="0">
            <tr>
                <td scope="row" colspan="2">交货日期</td>
                <td scope="row" colspan="12">${deliveryTime}</td>
            </tr>
            <tbody>
            <tr>
                <td>NO</td>
                <td>平台店铺</td>
                <td>图片</td>
                <td>目的国</td>
                <td>用友SKU</td>
                <td>用友SKU辅助码/老SKU</td>
                <td>贴标SKU</td>
                <td>中文描述</td>
                <td>件重尺</td>
                <td>装箱数</td>
                <td>单价</td>
                <td>数量</td>
                <td>金额</td>
                <td>子PO</td>
            </tr>
            <#list details! as t>
            <tr>
                <td>${t_index+1}</td>
                <td>
                    <div>${t.platform}</div>
                    <div>${t.store}</div>
                </td>
                <td>
                    <img style="width: 100px" src="${t.cinvcodeurl}" alt=""/>
                </td>
                <td>${t.siteName}</td>
                <td>${t.cinvcode}</td>
                <td>${t.oldsku}</td>
                <td>随批次甲方发放</td>
                <td>
                    <div style="width: 220px;">${t.cinvName}</div>
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
            <tr>
                <td class="td-bold" scope="row" colspan="2">合计体积:</td>
                <td scope="row" colspan="6">${totalVolume}</td>
                <td class="td-bold" scope="row" colspan="2">合计重量:</td>
                <td scope="row" colspan="4">${totalWeight}</td>
            </tr>
            <tr>
                <td class="td-bold" scope="row" colspan="2">合计金额(小写):</td>
                <td scope="row" colspan="6">${totalAmountMin}</td>
                <td class="td-bold" scope="row" colspan="2">合计金额(大写):</td>
                <td scope="row" colspan="4">${totalAmountMax}</td>
            </tr>
            <tr>
                <td class="td-bold" colspan="2">合计下单数量:</td>
                <td colspan="12">${totalSKUQty}</td>
            </tr>
            <tr>
                <td class="td-bold" colspan="2">注意事项:</td>
                <td colspan="12">${memo}</td>
            </tr>
            <tr>
                <td class="td-bold" colspan="2">合同有效性:</td>
                <td colspan="12">${changeMemo}</td>
            </tr>
            <tr>
                <td  colspan="14">${remark}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <table>
        <tbody>
            <tr>
                <td>
                    <div style="width: 60px;"></div>
                </td>
                <td>
                    <div><label>采购方代表(签字盖章):</label></div>
                    <div style="margin-top: 6px;"><label class="label-text">签署日期:</label></div>
                </td>
                <td>
                    <div><label class="label-text">供货方代表(签字盖章):</label></div>
                    <div style="margin-top: 6px;"><label class="label-text">签署日期:</label></div>
                </td>
            </tr>
        </tbody>
    </table>
    <div style="margin-top: 10px;">
        备注: <span>${remark}</span>
    </div>
</div>
</body>
</html>
