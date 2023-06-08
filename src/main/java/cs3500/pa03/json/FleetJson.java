package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;
import java.util.List;

public record FleetJson(@JsonProperty("fleet") List<ShipAdapterJSON> fleet) {
}
