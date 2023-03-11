function getCount(id) {
    let elementById = document.getElementById(id).value;
    if (Number(elementById) == 0) return ""
    else if (
        (elementById).localeCompare('0.') == 0 ||
        (elementById).localeCompare('0,') == 0 ||
        (elementById).localeCompare('0/') == 0 ||
        (elementById).localeCompare('0 ') == 0 ||
        (elementById).localeCompare('0*') == 0 ||
        (elementById).localeCompare('0:') == 0 ||
        (elementById).localeCompare('0-') == 0 ||
        (elementById).localeCompare('0.2') == 0 ||
        (elementById).localeCompare('0,2') == 0 ||
        (elementById).localeCompare('0 2') == 0 ||
        (elementById).localeCompare('0*2') == 0 ||
        (elementById).localeCompare('0:2') == 0 ||
        (elementById).localeCompare('0-2') == 0 ||
        (elementById).localeCompare('0/2') == 0
    ) {
        return "2/0"
    } else if (
        (elementById).localeCompare('0.4') == 0 ||
        (elementById).localeCompare('0,4') == 0 ||
        (elementById).localeCompare('0 4') == 0 ||
        (elementById).localeCompare('0*4') == 0 ||
        (elementById).localeCompare('0:4') == 0 ||
        (elementById).localeCompare('0-4') == 0 ||
        (elementById).localeCompare('0/4') == 0
    ) {
        return "4/0"

    } else if (Number(elementById) == 1 ||
        (elementById).localeCompare('1.') == 0 ||
        (elementById).localeCompare('1,') == 0 ||
        (elementById).localeCompare('1/') == 0 ||
        (elementById).localeCompare('1 ') == 0 ||
        (elementById).localeCompare('1*') == 0 ||
        (elementById).localeCompare('1:') == 0 ||
        (elementById).localeCompare('1-') == 0 ||
        (elementById).localeCompare('1.3') == 0 ||
        (elementById).localeCompare('1,3') == 0 ||
        (elementById).localeCompare('1 3') == 0 ||
        (elementById).localeCompare('1*3') == 0 ||
        (elementById).localeCompare('1:3') == 0 ||
        (elementById).localeCompare('1-3') == 0 ||
        (elementById).localeCompare('1/3') == 0
    ) {
        return "3/1"
    } else if (
        (elementById).localeCompare('1.4') == 0 ||
        (elementById).localeCompare('1,4') == 0 ||
        (elementById).localeCompare('1 4') == 0 ||
        (elementById).localeCompare('1*4') == 0 ||
        (elementById).localeCompare('1:4') == 0 ||
        (elementById).localeCompare('1-4') == 0 ||
        (elementById).localeCompare('1/4') == 0
    ) {
        return "4/1"
    } else if (
        (elementById).localeCompare('1.2') == 0 ||
        (elementById).localeCompare('1,2') == 0 ||
        (elementById).localeCompare('1 2') == 0 ||
        (elementById).localeCompare('1*2') == 0 ||
        (elementById).localeCompare('1:2') == 0 ||
        (elementById).localeCompare('1-2') == 0 ||
        (elementById).localeCompare('1/2') == 0
    ) {
        return "2/1"

    } else if (
        (elementById).localeCompare('2,0') == 0 ||
        (elementById).localeCompare('2 0') == 0 ||
        (elementById).localeCompare('2*0') == 0 ||
        (elementById).localeCompare('2:0') == 0 ||
        (elementById).localeCompare('2-0') == 0 ||
        (elementById).localeCompare('2.0') == 0 ||
        (elementById).localeCompare('2/0') == 0
    ) {
        return "0/2"
    } else if (
        (elementById).localeCompare('2.1') == 0 ||
        (elementById).localeCompare('2,1') == 0 ||
        (elementById).localeCompare('2 1') == 0 ||
        (elementById).localeCompare('2*1') == 0 ||
        (elementById).localeCompare('2:1') == 0 ||
        (elementById).localeCompare('2-1') == 0 ||
        (elementById).localeCompare('2/1') == 0
    ) {
        return "1/2"
    } else if (
        (elementById).localeCompare('2.4') == 0 ||
        (elementById).localeCompare('2,4') == 0 ||
        (elementById).localeCompare('2 4') == 0 ||
        (elementById).localeCompare('2*4') == 0 ||
        (elementById).localeCompare('2:4') == 0 ||
        (elementById).localeCompare('2-4') == 0 ||
        (elementById).localeCompare('2/4') == 0
    ) {
        return "4/2"
    } else if (Number(elementById) == 2 ||
        (elementById).localeCompare('2.') == 0 ||
        (elementById).localeCompare('2,') == 0 ||
        (elementById).localeCompare('2/') == 0 ||
        (elementById).localeCompare('2 ') == 0 ||
        (elementById).localeCompare('2*') == 0 ||
        (elementById).localeCompare('2:') == 0 ||
        (elementById).localeCompare('2-') == 0 ||
        (elementById).localeCompare('2.3') == 0 ||
        (elementById).localeCompare('2,3') == 0 ||
        (elementById).localeCompare('2 3') == 0 ||
        (elementById).localeCompare('2*3') == 0 ||
        (elementById).localeCompare('2:3') == 0 ||
        (elementById).localeCompare('2-3') == 0 ||
        (elementById).localeCompare('2/3') == 0) {
        return "3/2"
    } else if (Number(elementById) == 3 ||
        (elementById).localeCompare('3/') == 0 ||
        (elementById).localeCompare('3,') == 0 ||
        (elementById).localeCompare('3/') == 0 ||
        (elementById).localeCompare('3 ') == 0 ||
        (elementById).localeCompare('3*') == 0 ||
        (elementById).localeCompare('3:') == 0 ||
        (elementById).localeCompare('3-') == 0 ||
        (elementById).localeCompare('3,0') == 0 ||
        (elementById).localeCompare('3.0') == 0 ||
        (elementById).localeCompare('3 0') == 0 ||
        (elementById).localeCompare('3*0') == 0 ||
        (elementById).localeCompare('3:0') == 0 ||
        (elementById).localeCompare('3-0') == 0 ||
        (elementById).localeCompare('3/0') == 0) {
        return "0/3"
    } else if (
        (elementById).localeCompare('3.4') == 0 ||
        (elementById).localeCompare('3,4') == 0 ||
        (elementById).localeCompare('3 4') == 0 ||
        (elementById).localeCompare('3*4') == 0 ||
        (elementById).localeCompare('3:4') == 0 ||
        (elementById).localeCompare('3-4') == 0 ||
        (elementById).localeCompare('3/4') == 0
    ) {
        return "4/3"
    } else if ((elementById).localeCompare('3/1') == 0 ||
        (elementById).localeCompare('3.1') == 0 ||
        (elementById).localeCompare('3,1') == 0 ||
        (elementById).localeCompare('3 1') == 0 ||
        (elementById).localeCompare('3*1') == 0 ||
        (elementById).localeCompare('3:1') == 0 ||
        (elementById).localeCompare('3-1') == 0) {
        return "1/3"
    } else if ((elementById).localeCompare('3/2') == 0 ||
        (elementById).localeCompare('3,2') == 0 ||
        (elementById).localeCompare('3 2') == 0 ||
        (elementById).localeCompare('3*2') == 0 ||
        (elementById).localeCompare('3:2') == 0 ||
        (elementById).localeCompare('3.2') == 0 ||
        (elementById).localeCompare('3-2') == 0) {
        return "2/3"

    } else if (Number(elementById) == 4 ||
        (elementById).localeCompare('4.0') == 0 ||
        (elementById).localeCompare('4,0') == 0 ||
        (elementById).localeCompare('4 0') == 0 ||
        (elementById).localeCompare('4*0') == 0 ||
        (elementById).localeCompare('4:0') == 0 ||
        (elementById).localeCompare('4-0') == 0 ||
        (elementById).localeCompare('4/0') == 0
    ) {
        return "0/4"
    } else if (
        (elementById).localeCompare('4.1') == 0 ||
        (elementById).localeCompare('4,1') == 0 ||
        (elementById).localeCompare('4 1') == 0 ||
        (elementById).localeCompare('4*1') == 0 ||
        (elementById).localeCompare('4:1') == 0 ||
        (elementById).localeCompare('4-1') == 0 ||
        (elementById).localeCompare('4/1') == 0
    ) {
        return "1/4"
    } else if (
        (elementById).localeCompare('4.2') == 0 ||
        (elementById).localeCompare('4,2') == 0 ||
        (elementById).localeCompare('4 2') == 0 ||
        (elementById).localeCompare('4*2') == 0 ||
        (elementById).localeCompare('4:2') == 0 ||
        (elementById).localeCompare('4-2') == 0 ||
        (elementById).localeCompare('4/2') == 0
    ) {
        return "2/4"
    } else if (
        (elementById).localeCompare('4.3') == 0 ||
        (elementById).localeCompare('4,3') == 0 ||
        (elementById).localeCompare('4 3') == 0 ||
        (elementById).localeCompare('4*3') == 0 ||
        (elementById).localeCompare('4:3') == 0 ||
        (elementById).localeCompare('4-3') == 0 ||
        (elementById).localeCompare('4/3') == 0
    ) {
        return "3/4"
    }
    return "3/0"
}