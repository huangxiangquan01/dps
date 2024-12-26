<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
</head>
<style type="text/css">
@page { size: A4 }
body { margin: 0 }
.sheet {
  margin: 0;
  overflow: hidden;
  position: relative;
  box-sizing: border-box;
  page-break-after: always;
}

/** Paper sizes **/
body.A3               .sheet { width: 297mm; height: 419mm }
body.A3.landscape     .sheet { width: 420mm; height: 296mm }
body.A4               .sheet { width: 210mm; height: 296mm }
body.A4.landscape     .sheet { width: 297mm; height: 209mm }
body.A5               .sheet { width: 148mm; height: 209mm }
body.A5.landscape     .sheet { width: 210mm; height: 147mm }
body.letter           .sheet { width: 216mm; height: 279mm }
body.letter.landscape .sheet { width: 280mm; height: 215mm }
body.legal            .sheet { width: 216mm; height: 356mm }
body.legal.landscape  .sheet { width: 357mm; height: 215mm }

/** Padding area **/
.sheet.padding-10mm { padding: 10mm }
.sheet.padding-15mm { padding: 15mm }
.sheet.padding-20mm { padding: 20mm }
.sheet.padding-25mm { padding: 25mm }

/** For screen preview **/
@media screen {
  body { background: #e0e0e0 }
  .sheet {
    background: white;
    box-shadow: 0 .5mm 2mm rgba(0,0,0,.3);
    margin: 5mm auto;
  }
}

/** Fix for Chrome issue #273306 **/
@media print {
           body.A3.landscape { width: 420mm }
  body.A3, body.A4.landscape { width: 297mm }
  body.A4, body.A5.landscape { width: 210mm }
  body.A5                    { width: 148mm }
  body.letter, body.legal    { width: 216mm }
  body.letter.landscape      { width: 280mm }
  body.legal.landscape       { width: 357mm }
}
</style>
<style>
.pdf-wrapper {
    font-size: 14px;
}
.title {

}
.title {
    text-align: center;
}
.item-column .label-text {
    display: inline-block;
    width: 80px;
    font-weight: bold;
}
.label-text {
    font-weight: bold;
}
table {
    width: 100%;
    th,td {
        text-align: center;
    }
}
.top-content {
    display: flex;
}
.item-column {
    flex: 1;
}
.td-bold {
    font-weight: bold;
}
.gz {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    padding: 0 180px;
}
</style>
<body class="A4 landscape">
    <div class="pdf-wrapper sheet padding-10mm">
        <h1 class="title">采购订单</h1>
        <div class="top-content">
            <div class="item-column" style="color:red; display: table-cell; width: 40%">
                <div>
                    <label class="label-text">采购订单号:</label>
                    <span>${cpoid}</span>
                </div>
                <div>
                    <label class="label-text">采购方:</label>
                    <span>${purchasecom}</span>
                </div>
                <div>
                    <label class="label-text">收货方:</label>
                    <span>${receivePerson}</span>
                </div>
                <div>
                    <label class="label-text">联系人:</label>
                    <span>${purchaseLink}</span>
                </div>
                <div>
                    <label class="label-text">联系电话</label>
                    <span>${linkPhone}</span>
                </div>
                <div>
                    <label class="label-text">公司电话</label>
                    <span>${companytel}</span>
                </div>
            </div>
            <div class="item-column" style="display: table-cell; width: 40%">
                <div>
                    <label></label>
                    <span></span>
                </div>
                <div>
                    <label class="label-text">供货方:</label>
                    <span>${supplyCode}</span>
                </div>
                <div>
                    <label class="label-text">地址:</label>
                    <span>${supplyAddress}</span>
                </div>
                <div>
                    <label class="label-text">联系人:</label>
                    <span>${supplyLink}</span>
                </div>
                <div>
                    <label class="label-text">联系电话:</label>
                    <span>${supplyLinkphone}</span>
                </div>
                <div>
                    <label class="label-text"></label>
                    <span ></span>
                </div>
            </div>
            <div class="item-column" style="display: table-cell; width: 20%">
                <div>
                    <label class="label-text">下单日期:</label>
                    <span>${createtime}</span>
                </div>
                <div>
                    <label class="label-text">采购员:</label>
                    <span>${purchase}</span>
                </div>
                <div>
                    <label class="label-text">打印日期:</label>
                    <span>${printtime}</span>
                </div>
                <div>
                    <label class="label-text"></label>
                    <span></span>
                </div>
                <div>
                    <label class="label-text">税率:</label>
                    <span>${rate}</span>
                </div>
                <div>
                    <label class="label-text">币种:</label>
                    <span >${exchName}</span>
                </div>
            </div>
        </div>
        <div class="table-wrapprt">
            <table border="1" cellspacing="0" cellpadding="0">
                <thead>
                    <tr>
                        <th scope="row" colspan="2">交货日期</th>
                        <th scope="row" colspan="12">${deliveryTime}</th>
                    </tr>

                </thead>
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
        <div class="gz">
            <div>
                <div><label class="label-text">采购方代表(签字盖章):</label></div>
                <div style="margin-top: 6px;"><label class="label-text">签署日期:</label></div>
            </div>
           <div>
                <div>
                    <label class="label-text">供货方代表(签字盖章):</label>
                </div>
                <div style="margin-top: 6px;">
                    <label class="label-text">签署日期:</label>
                </div>
           </div>
        </div>
        <div style="margin-top: 10px;">
            备注: <span>${remark}</span>
        </div>
    </div>
</body>
</html>
