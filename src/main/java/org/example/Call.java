package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Call {
    CallType callType;
    String phoneNumber;
    LocalDateTime dateTimeStart;
    LocalDateTime dateTimeEnd;
    TariffType tariffType;

    double rubCost;

    public Call(
            CallType callType,
            String phoneNumber,
            LocalDateTime dateTimeStart,
            LocalDateTime dateTimeEnd,
            TariffType tariffType
    ) {
        this.callType = callType;
        this.phoneNumber = phoneNumber;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.tariffType = tariffType;
    }

    Duration getDuration() {
        return Duration.between(dateTimeStart, dateTimeEnd);
    }
}
