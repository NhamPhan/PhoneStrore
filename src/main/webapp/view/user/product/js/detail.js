const btnSubtractDOM = document.querySelector('.btn-subtract');
const btnPlusDOM = document.querySelector('.btn-plus');
const inputQuantityDOM = document.querySelector('input[name=quantity]');

const TYPE_PLUG = 'TYPE_PLUS';
const TYPE_SUBTRACT = 'TYPE_SUBTRACT';

const eventClick = (event, type) => {

    const { min, max, value } = inputQuantityDOM;

    if (type === TYPE_PLUG) {
        if (value == max) {
            toastr.error('Không đủ số lượng sách')
            return;
        }
        inputQuantityDOM.value = parseInt(value) + 1;
    }

    if (type === TYPE_SUBTRACT) {
        if (value == min) {
            toastr.error('Số lượng sản phẩm phải lớn hơn 0');
            return;
        }
        inputQuantityDOM.value = parseInt(value) - 1;
    }
}

btnSubtractDOM.addEventListener('click', e => {
    eventClick(e, TYPE_SUBTRACT);
})

btnPlusDOM.addEventListener('click', e => {
    eventClick(e, TYPE_PLUG);
})