

function appendDummyRow(discardHistories, pageLength, startRowNo) {
    if (discardHistories.length % pageLength !== 0 || discardHistories.length === 0) {
        const dummySpaceLen = pageLength - (discardHistories.length % pageLength);
        for (let i = 0; i < dummySpaceLen; i++) {
            discardHistories.push({
                "hiddenOrder": startRowNo,
                "no": null,
                "productCode": null,
                "productName": "&nbsp;",
                "quantity": null,
                "date": null
            });
            startRowNo += 1;
        }
    }
    return discardHistories;
}