const forms = document.getElementsByName('statistics');

const param = {
    body: 'statistics',
    url: '/admin/statistics/sales',
    method: 'post',
    responseHandlers: [(data) => changeChart(JSON.parse(data))]
}

const statisticsItem = document.getElementsByClassName('statistics__item')[0];
const statisticsItemChart = statisticsItem.getElementsByClassName('statistics__item__chart')[0];
const statisticsItemChartCanvas = statisticsItemChart.getElementsByClassName('statistics__item__chart__canvas')[0];
const chart = new Chart(statisticsItemChartCanvas, {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            data: [],
            label: 'Выручка',
            backgroundColor: 'rgba(85,200,255,0.4)',
            borderColor: 'rgb(71 153 235)',
            borderWidth: 2,
            radius: 5
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    stepSize: 5,
                    maxTicksLimit: 10,
                    fontColor: 'rgb(218 223 227)'
                }
            }],
            xAxes: [{
                gridLines: {
                    display: false
                },
                ticks: {
                    fontColor: 'rgb(218 223 227)'
                }
            }]
        },
        tooltips: {
            titleMarginBottom: 10,
            titleFontSize: 14,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            intersect: false,
            mode: 'index',
            caretPadding: 10
        },
        maintainAspectRatio: false
    }
});


function changeChart(data) {
    chart.data.labels = data.map(item => {
        const date = new Date(item[0]);
        return date.getDate() + '.' + (Number(date.getMonth()) + 1) + '.' + date.getFullYear();
    });
    chart.data.datasets[0].data = data.map(item => item[1]);
    chart.update();
}