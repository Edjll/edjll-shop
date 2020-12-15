<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/table/table.css">
    <@edjllForm.links></@edjllForm.links>
    <link rel="stylesheet" href="/static/css/statistics/statistics.css">
</#assign>

<#assign body>
    <main>
        <div class="statistics">
            <section class="statistics__item">
                <header class="statistics__item__header">
                    <h5><b>Продажи</b></h5>
                    <div class="statistics__item__header__controls">
                        <form name="statistics">
                            <div class="edjll-input">
                                <div class="edjll-input__settings">
                                    <span class="edjll-input__settings__attributes">{"name": "dateStart", "label": "С", "type": "date", "defaultValue":"${(.now?long - 1000 * 60 * 60 * 24 * 7)?number_to_date?iso_m_nz("Europe/Samara")}"}</span>
                                    <span class="edjll-input__settings__validation">{"required":true}</span>
                                </div>
                            </div>
                            <div class="edjll-input">
                                <div class="edjll-input__settings">
                                    <span class="edjll-input__settings__attributes">{"name": "dateEnd", "label": "До", "type": "date", "defaultValue":"${.now?date?iso_m_nz("Europe/Samara")}"}</span>
                                    <span class="edjll-input__settings__validation">{"required":true, "laterThan": "dateStart"}</span>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="statistics__item__header__info">
                        <div class="statistics__item__header__info__item">
                            <span class="statistics__item__header__info__item__text">Выручка по продажам</span>
                        </div>
                    </div>
                </header>
                <div class="statistics__item__chart">
                    <canvas class="statistics__item__chart__canvas"></canvas>
                </div>
            </section>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/chart.js/dist/Chart.js"></script>
    <script src="/static/js/stats/index.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/stats/action.js"></script>
</#assign>

<@template.adminPage
bodyBody = body
links=links
scripts = scripts
page = "Акции"
></@template.adminPage>