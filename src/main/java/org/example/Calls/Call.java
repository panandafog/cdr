package org.example.Calls;

import org.example.Tariffs.TariffType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Call {
    public CallType callType;
    public String phoneNumber;
    public LocalDateTime dateTimeStart;
    public LocalDateTime dateTimeEnd;
    public TariffType tariffType;

    public double rubCost;

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

    public Duration getDuration() {
        return Duration.between(dateTimeStart, dateTimeEnd);
    }
}
