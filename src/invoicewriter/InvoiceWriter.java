package invoicewriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InvoiceWriter extends HttpServletResponseWrapper {

    private final CharArrayWriter invoiceWriter = new CharArrayWriter();

    public InvoiceWriter(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(invoiceWriter);
    }

    public String getOutput() {
        return invoiceWriter.toString();
    }
}
