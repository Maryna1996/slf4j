package org.example;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    private Client client;
    private PrintWriter mockOut;

    @Before
    public void setup() {
        client = new Client();
        mockOut = Mockito.mock(PrintWriter.class);
        client.setOut(mockOut); // Inject the mocked PrintWriter into the client
    }

    @Test
    public void testSendMessage() {
        String expectedMessage = "Hello, Server";

        // Mock the behavior of the PrintWriter and verify the method call
        Mockito.doNothing().when(mockOut).println(expectedMessage);

        // Send the message using the client
        String actualResponse = client.sendMessage(expectedMessage);

        // Check if the response matches the expected message
        assertEquals(expectedMessage, actualResponse);

        // Verify that the println method was called with the expected message
        Mockito.verify(mockOut).println(expectedMessage);
    }
}

