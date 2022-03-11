package ro.cburcea.playground.spring.batch.skip;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Trade, Trade> {

    public Trade process(Trade trade) {
        if(trade.getPrice() < 0 || trade.getQuantity() < 0) {
            throw new InvalidTradeException();
        }
        return trade;
    }
}