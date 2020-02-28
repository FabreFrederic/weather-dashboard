'use strict';

const arduinoSerialPort = require('serialport');
const separator = '\n';

const findAvailableArduinoSerialPortName = async () => {
    const ports = await arduinoSerialPort.list();
    // Port name is usually '/dev/ttyUSB0'
    return ports.find(port => port.path && port.manufacturer);
};

module.exports.initializeSerialPort = async () => {
    const arduinoPort = await findAvailableArduinoSerialPortName();

    if (arduinoPort && arduinoPort.path) {
        console.log('The Arduino has been found : ', arduinoPort);
        console.log();
    } else {
        console.log('Error - Please connect the Arduino');
        process.exit(1);
    }

    const serialPort = new arduinoSerialPort(arduinoPort.path, {
        baudRate: 9600,
        dataBits: 8,
        parity: 'none',
        stopBits: 1,
        flowControl: false,
        parser: new arduinoSerialPort.parsers.Readline(separator)
    });

    serialPort.on('error', function (err) {
        console.log('Error while opening the Arduino port :' + arduinoPort, err);
        console.log();
    });

    serialPort.on('open', function () {
        console.log('Serial port opened', arduinoPort.path);
        console.log();
    });

    serialPort.on('close', function (err) {
        console.log('Serial port closed', err);
    });
    return serialPort;
};