/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itextpdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.String;

/**
 *
 * @author carlos
 */
public class GeneratePdf {

    Document document;
    FileOutputStream pdfFile;

    public GeneratePdf(String fileName) throws FileNotFoundException, DocumentException {

        this.document = new Document();
        this.pdfFile = new FileOutputStream(fileName);

    }

    public void openPdf(int spaceLeading) throws DocumentException {
        PdfWriter.getInstance(document, pdfFile).setInitialLeading(spaceLeading);
        document.open();
    }

    public void addElements() throws DocumentException {
        document.add(new Paragraph("Ejemplo primer párrafo, Ejemplos del mundo"));
        document.add(new Paragraph("Segundo titulo del mundo", FontFactory.getFont("arial", 22, Font.ITALIC, BaseColor.CYAN)));
    }

    public void addPhoto(String photoName) {
        try {
            Image image = Image.getInstance(photoName);
            image.scaleToFit(400, 400);
            image.setAlignment(Chunk.ALIGN_MIDDLE);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTable() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        for (int i = 0; i < 15; i++) {
            table.addCell("cell" + i);
        }
        document.add(table);

    }

    public void closePdf() {
        document.close();
    }

    public void htmlDocument() throws IOException {
        try {
            String htmlTag = "<html><head>\n" +
"    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n" +
"\n" +
"    <script type=\"text/javascript\">\n" +
"        google.load(\"visualization\", '1', {packages:['corechart']});\n" +
"        google.setOnLoadCallback(drawChart);\n" +
"        function drawChart() {\n" +
"\n" +
"            var data = google.visualization.arrayToDataTable([\n" +
"                ['Element', 'Density', { role: 'style' }],\n" +
"                ['Copper', 8.94, '#b87333', ],\n" +
"                ['Silver', 10.49, 'silver'],\n" +
"                ['Gold', 19.30, 'gold'],\n" +
"                ['Platinum', 21.45, 'color: #e5e4e2' ]\n" +
"            ]);\n" +
"\n" +
"            var options = {\n" +
"                title: \"Density of Precious Metals, in g/cm^3\",\n" +
"                bar: {groupWidth: '95%'},\n" +
"                legend: 'none',\n" +
"            };\n" +
"\n" +
"            var chart_div = document.getElementById('chart_div');\n" +
"            var chart = new google.visualization.ColumnChart(chart_div);\n" +
"\n" +
"            // Wait for the chart to finish drawing before calling the getImageURI() method.\n" +
"            google.visualization.events.addListener(chart, 'ready', function () {\n" +
"                chart_div.innerHTML = '<img src=\"' + chart.getImageURI() + '\">';\n" +
"                console.log(chart_div.innerHTML);\n" +
"            });\n" +
"\n" +
"            chart.draw(data, options);\n" +
"\n" +
"        }\n" +
"    </script><script src=\"https://www.google.com/uds/?file=visualization&amp;v=1&amp;packages=corechart\" type=\"text/javascript\"></script><link href=\"https://www.google.com/uds/api/visualization/1.0/0bcce42a8d59504364a795cd2cb95c49/ui+es.css\" type=\"text/css\" rel=\"stylesheet\"><script src=\"https://www.google.com/uds/api/visualization/1.0/0bcce42a8d59504364a795cd2cb95c49/format+es,default+es,ui+es,corechart+es.I.js\" type=\"text/javascript\"></script>\n" +
"\n" +
"    <link href=\"https://ajax.googleapis.com/ajax/static/modules/gviz/1.0/core/tooltip.css\" rel=\"stylesheet\" type=\"text/css\"></head><body><div id=\"chart_div\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAB3AAAADICAYAAAAZQnDQAAAgAElEQVR4Xu3dCbzlY+E/8GcUkrGVbEW2FlsSlVLSgpSlTdFmyTIplSjrzy4tEgoZWYexVaKNJFJpG0kUpUGLtCAMFcr8X5/v6/W9/zt37sw9M3POud9zzvt5vebF3Ps9z/d53s937jn3+/k+zzNh5syZM4tCgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAuMuMEGAO+5joAEECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBCoBAS4LgQCBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAg0RECA25CB0AwCBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgIcF0DBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQaIiAALchA6EZBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQEOC6BggQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQINAQAQFuQwZCMwgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQICDAdQ0QIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECgIQIC3IYMhGYQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIEBAgOsaIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAQEMEBLgNGQjN6F2BJ554oiy00EK92wEtb7uAa6LtpCokQIAAAQIECBAgQIAAAQIECBAgQIAAAQIDIyDAHZih7o2OPv/5zy+//e1vhxqbYHSJJZYoL3zhC8vHP/7x8oY3vGHcOjJp0qRy2mmnlT333LN88YtfrNrxk5/8pOy3337lhz/8YVfadf/995f3ve995aqrrqrO9573vKeceuqps5z73nvvLc94xjNm+dqTnvSkMnHixMrxk5/8ZNl444270t4LL7yw7LjjjuV5z3teue2227pyzrFO8uc//7msvPLKQ4cde+yx5YADDhj6+7vf/e5y/vnnV39ffPHFy8MPPzxWlUPf//e//12OPvrossIKK5S999675deNdm21/OK5HDhe/pdffnnZbrvtytSpU6vx73T53//+V57znOeUO++8s3z9618vW2+99SynvOOOO8rHPvaxcvXVV5eFF164vPzlLy+f/exny5prrtnppqmfAAECBAgQIECAAAECBAgQIECAAAECBAjMs4AAd57JvKCTAiMD3OHnmjBhQjn99NOrAHM8ysiQbdq0aeUlL3lJeepTnzpPId+CtP2oo44qhx56aHnyk59c1lprrfLmN7+5HHHEEbNUOVqAO/yAtPdXv/pVWWONNRakKS29drwCxLk1bmSAu+WWW5Yrrrhi6CXPetazyt133139fV4D3G222aZ84xvfKJ/5zGeqYL/V0m8BbsLS4447rsT6mc98ZqsM833cxRdfXN7xjndUr3/ta19bvvvd7w7V9a9//av6t/LHP/6xCm9THn/88bLccsuVX//612XZZZed7/N6IQECBAgQIECAAAECBAgQIECAAAECBAgQ6ISAALcTquqcb4E6wD3ssMPKRz7ykfLoo4+W6dOnV7Nvf/SjH5XFFlusCmLGI3T529/+Vv75z3+WZZZZpiy//PLl2muvLa9+9avnOeSbb5xSyl577VXNuM0s0SlTpoxa1fAA99vf/nY12zaOP/jBD6oZu//5z38qz0996lML0pSWXjtjxowqDF100UXLaqut1tJrOn3Q8AB3kUUWqUK9jGv+e/vtt5fnPve5JV9/7LHH5nlsN9tss/L973+/MQHuePnnmvv73/9eMvO1G+WlL31p+dnPflb9u8y/0zygsN5661Wnvuiii8oOO+xQ1llnnWqm/MyZM6vv5bo866yzys4779yNJjoHAQIECBAgQIAAAQIECBAgQIAAAQIECBBoWUCA2zKVA7shUAe4I5e1TSi5yiqrlCxRe9JJJw0tT/vLX/6y7LvvvuX666+vwrbMgMzsxzrgPe+886rQ8q1vfWsVtn7uc58rf/rTn6qlhE855ZSy4YYbVt36xz/+UQ466KBy5ZVXVsFTXr/VVluVT3/601VgmzJ8lmQCodQ3vOS811xzTfnWt75Vdtlll3LmmWcOfbsOmBKaJjwdrSQ8zOzaLPOa4C0hU5b2fctb3lIdnjbfdNNNs7w050toOLwMD3AT2r7iFa8Y+vbmm29ezU7MbMXMjj3hhBPKPvvsU4VYOWfavsUWW5Svfe1rJfXENsvhJszcZJNNKo+0oy5/+MMfyv7771++853vVMdkpuPBBx9c3vSmN1WHzGkGbpaA/sQnPlFuuOGGkpnVm266aTnmmGPKC17wgup1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VXvqZvZfwr+xSgLKww47rArAslR0gsHUmSCvLt0KcHO+BKUJSBPcJeTMstD5e5aFrktmBie4zrLTCfSyVHXanNnPKaMFiPl6ZhtndmwdZMY6s1iH7/8buwSiOSYBcpZETrB5/vnnD4WYCQ2zT2xC7sxIzQzouGVJ4DktHT0ywM3y1Qnx8kBA9sHNEsmjBbhpd2bMZiZyxirBfALNBMj1HsZpcwLbhIWZZZuAMNdGxi1LcedazqzVhOJZZrmeATratZV9fY899tjyq1/9qrJdc801y957713NXE6pA9xc87n+RyvtDHBT/5QpUyrb/BvJrN+99tqrWma7Du8zuzr/VjNDd3gZ6zrpZICbdiRoz7Wb2daZ3Z0l13P91T8nxvq36fsECBAgQIAAAQIECBAgQIAAAQIECBAgQKCbAgLcbmo718AIZDni7IeaUu+hOzCd19GuCZx00klVeJ6lrTtdspx3ZjhnNvQOO+xQna5enjh7Bie0VggQIECAAAECBAgQIECAAAECBAgQIECAAIEFFxDgLrihGggMCWTmYZZ4zvK6WRY4s0GzDHBmEisE2imQvWezN3D2yM1euZ0u2Zc3s49TMrs8yxHfeuut1TLPmT2dfX8VAgQIECBAgAABAgQIECBAgAABAgQIECBAYMEFBLgLbqgGAkMC2ct20003rZa8zZ62kydPrpb/VQi0WyABbvZqHr6kdbvPMby+7It80EEHVXv4/ulPf6oeSsi1vfPOO1dLh2dvWYUAAQIECBAgQIAAAQIECBAgQIAAAQIECBBYcAEB7oIbqoEAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQJtERDgtoVRJQQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFhwAQHughuqgQABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAm0REOC2hVElBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQWHABAe6CG6qBAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECbREQ4LaFsT8q+cEPflAuuOCC8qc//akstdRSZZNNNik777xzWWyxxWbr4KOPPlr22GOPstFGG5W99967PwD0ggABAgQIECBAgAABAgQIECBAgAABAgQIECBAgMA4Cwhwx3kAmnL666+/vhx22GHljW98YxXc3n333eWcc84p6667bjnqqKNma+app55avvrVr5Ztt91WgNuUQdQOAgQIECBAgAABAgQIECBAgAABAgQIECBAgACBnhcQ4Pb8ELanAx/+8IfL4osvXj7xiU8MVXjVVVeVT3/60+Xss88uz3zmM4e+/utf/7occMABZZFFFimbbbaZALc9Q6AWAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAkWA6yKoBKZMmVLWXHPN8rKXvWxI5LbbbqvC2YS4G2ywQfX1xx57rOy5555lq622Kt/5znfK+uuvL8B1DREgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBok4AAt02Q/VhNZt5mT9yLLrqoLL300lUXJ0+eXG666aby+c9/vtoDV4DbjyOvTwQIECBAgAABAgQIECBAgAABAgQIECBAgAABAuMlIMAdL/mGn/e3v/1t2WeffcrWW29d9tprr6q1t956a9l3333LF77whbL66quX3XbbTYDb8HHUPAIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAgd4SEOD21nh1pbW333572X///asllY855piy8MILl8cff7xMmjSpbLrppmWnnXaq2tHuAPeGG27oSv+chAABAgQIECBAgAABAgQIECBAgAABAgQIECAwmsCGG24IhsC4Cwhwx30ImtWAhKhHHHFEWXfddcuhhx5anvKUp1QNPOOMM8o111xTjj/++PLkJz+5+lpm466zzjpl1113rZZYXmihhZrVGa0hQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAg0GMCAtweG7BONvf73/9+OfbYY8urX/3qKpytg9qcM8soZ2bunMqUKVPKCius0MnmqZsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIBA3wsIcPt+iFvr4E033VQtm7zddtuV97///bO96De/+U155JFHZvn6CSecUO2Fu+2225YXvOAFZdFFF23tZI4iQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQGBUAQGuC6M88cQTZZdddikPPvhgec973lMmTJgwi8rGG29cVlpppdmk2r0HrqEgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgMOgCAtxBvwJKKXfccUfZc8895yhx+OGHl0022USA61ohQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAg0GEBAW6HgVVPgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgACBVgUEuK1KOY4AAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIdFhDgdhhY9QQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEGhVQIDbqpTjCBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAg0GEBAW6HgVVPgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgACBVgUEuK1KOY4AAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIdFhDgdhhY9QQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEGhVQIDbqpTjCBAgQIAAAQIECBAgQIAAAQIECBDoe4HHHn105qOP/qfv+6mDBAgQGCSBRRd9Sllk0UUnDFKf9bW3BQS4vT1+Wk+AAAECBAgQIECAAAECBAgQIECAQBsFZjz04MwZMx5qY42qIkCAAIFxFZhQyhITlyxLLLmUAHdcB8LJ50VAgDsvWo4lQIAAAQIECBAgQIAAAQIECBAgQKCvBYYFuG709/VI6xwBAgMjMGHCzCUmLiHAHZgB74+OCnD7Yxz1ggABAgQIECBAgAABAgQIECBAgACBNggIcNuAqAoCBAg0SUCA26TR0JYWBQS4LUI5jAABAgQIECBAgAABAgQIECBAgACB/hcQ4Pb/GOshAQIDJiDAHbAB74/uCnD7Yxz1ggABAgQIECBAgAABAgQIECBAgACBNggIcNuAqAoCBAg0SUCA26TR0JYWBQS4LUI5jAABAgQIECBAgAABAgQIECBAgACB/hcQ4Pb/GOshAQIDJiDAHbAB74/uCnD7Yxz1ggABAgQIECBAgAABAgQIECBAgACBNggIcNuAqAoCBAg0SUCA26TR0JYWBQS4LUI5jAABAgQIECBAgAABAgQIECBAgACB/hcQ4Pb/GOshAQIDJiDAHbAB74/uCnD7Yxz1ggABAgQIECBAgAABAgQIECBAgACBNggIcNuAqAoCBAg0SUCA26TR0JYWBQS4LUI5jAABAgQIECBAgAABAgQIECBAgACB/hcQ4Pb/GOshAQIDJiDAHbAB74/uCnD7Yxz1ggABAgQIECBAgAABAgRaEfjXtTPLv65p5UjHECBAgECvCDz11aU8dbMJ7WquALddkuohQIBAQwQEuA0ZCM2YFwEB7rxoDcixDzzwQNl1113LMcccU9Zaa61Zen355ZeXSy+9tNx7771l5ZVXLrvsskt58YtfPCAyukmAAAECBAgQIECAQM8L3HvYzHLvkT3fDR0gQIAAgWECyx5ayrJHCHBdFAQIECAwuoAA15XRg/dqLQQAACAASURBVAIC3B4ctE42+aGHHioHHHBAuf3228tJJ500S4D7rW99q3zuc58rO+64Y1lvvfXK9773verPCSecMFvQ28k2qpsAAQIECBAgQIAAAQLzLfD/A9y23eif77Z4IQECBAi0Q2BmEeC2w1EdBAgQ6F8BAW7/jm0f90yA28eDO69d+9nPflZOPPHE8u9//7vMmDFjtgD3Qx/6UFlmmWXKEUccUVX9xBNPlJ133rmsv/76Zd99953X0zmeAAECBAgQIECAAAEC3RcQ4Hbf3BkJECDQWQEBbmd91U6AAIHeFxDg9v4YDmAPBLgDOOhz6vIWW2xR8mfzzTcv++2332wB7vve977yvOc9r3z84x8fquIDH/hAecYznlEOP/xwkgQIECBAgAABAgQIEGi+gAC3+WOkhQQIEJg3AQHuvHk5mgABAoMnIMAdvDHvgx4LcPtgENvVhbvuuqusuuqqZfr06WXSpEmzBbhTp04t+XPooYeWddZZp1x77bXV8smHHHJIedWrXtWuZqiHAAECBAgQIECAAAECnRMQ4HbOVs0ECBAYHwEB7vi4OysBAgR6R0CA2ztjpaVDAgJcF8NsAnMKcLNk8jHHHFOuu+66oddkP9xdd92VIgECBAgQIECAAAECBHpDQIDbG+OklQQIEGhdQIDbupUjCRAgMJgCAtzBHPce77UAt8cHsBPNn1OA+5nPfKb86Ec/qva9XW211cqNN95YLrzwwmq27pve9KYFbsoNN9ywwHWogAABAgQIECBAgAABAnMTWGmR02auuPDpOWQCKQIECBDoC4GZ9zy+e/nLY3u27ef6EhMXnzlx4uLeK/ri8tAJAgQI5JP/hJkPz3i4zHj4kZbeKzbccENsBMZdQIA77kPQvAaMFuDeeeedZY899igHHHBAee1rXzvU6LPOOqt85StfKZdccklZbLHFmtcZLSJAgAABAgQIECBAgMBwATNwXQ8ECBDoNwEzcPttRPWHAAEC7RYwA7fdourrgoAAtwvI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1lxzzXLggQdWEE888USZNGlSWWONNcr+++/fFRwBbleYnYQAAQJdExDgdo3aiQgQINCzAgLcnh06DSdAgEC3BAS43ZJ2HgIECPSqgAC3V0duoNstwB3o4W+98/fdd1/ZYYcdyqGHHlpe+cpXDr3wrLPOKt/85jfLl7/85dYrW4AjBbgLgOelBAgQaKCAALeBg6JJBAgQaJiAALdhA6I5BAgQaJ6AALd5Y6JFBAgQaJaAALdZ46E1LQkIcFtictCvfvWrsu+++5bTTjutrL766kMgV1xxRfnsZz9bLr300jJx4sSOQwlwO07sBAQIEOiqgAC3q9xORoAAgZ4UEOD25LBpNAECBLopIMDtprZzESBAoBcFBLi9OGoD32YB7sBfAq0B/PjHP65m306ZMqWssMIKQy/6/ve/X44++uhy/vnnl+WWW661yhbgqAS4d3xn8gLU4KUECBAg0DSB1bfYo6y55Z4T2tWuu+66a+Yf//jHdlWnHgIECBBogMAqq6xSVl111fa8V9x72Mxy75EN6JUmECBAgEDbBJY9tJRlj2jP+0QpZcZDD86c8fBDpZS2Vdm2rqqIAAECBOZHYGZZYuKSZYkll/KDfX74vGZcBAS448Leeye9/vrry2GHHVbOO++8svzyyw914LrrritHHXVUmTp1annGM56xQB3baKONxnz9akvPnLnaUmMe5gACBAgQ6CGBOx8s5c4HJrTtA/Qaa6wxM/uzKwQIECDQPwLTp08v06dPb8t7xYZrzZi50doz+gdHTwgQIECgTPvNEuWGW5doy/tEONdbd92Z6623LlkCBAgQ6COBm2++pdx8yy0tvVdMmzatj3quK70qIMDt1ZHrcrvrJZQnT55cVltttaGzX3nlleW4444rl112WXnqU5/a5VY5HQECBAgQIECAAAECBAgQIECAAAECBAgQIECAAIH+EhDg9td4dqw39913X9lhhx3KEUccUV7+8pcPnefss88u3/72t8tFF13UsXOrmAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgMCgCAhwB2Wk29DPXXfdtay99tplv/32q2p74oknyqRJk8rqq69eDjjggDacQRUECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIEBltAgDvY4z9Pvb/qqqvKpz/96fK2t72tbLDBBtXM25/+9Kfl5JNPnmVZ5Xmq1MEECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECAwJCHBdDPMkcPnll5eLL7643H///VVou9tuu1VhrkKAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAwIILCHAX3FANBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQaIuAALctjCohQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIDAggsIcBfcUA0ECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBoi4AAty2MKiFAgAABAgQIECBAgAABAgQIECBAORpZ1gAAE6ZJREFUgAABAgQIECBAgMCCCwhwF9xQDQQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEGiLgAC3LYwqIUCAAAECBAgQIECAAAECBAgQIECAAAECzRSYOXNmmTBhQjMbp1UECBAgMJuAANdFQYAAgQUQuP/++8sZZ5xRrrzyyvKXv/ylLLPMMuXFL35x2Xvvvcsaa6yxADV7KQECBAj0s8Bdd91VTjzxxHL99deXBx54oDz96U8vr3zlK8uHPvSh8sxnPnOo6y984QvLpEmTqj9f/OIXqz+//OUv+5lG3wgQIEBgDgLf+ta3ykUXXVRuu+228uCDD5bll1++bLbZZuX9739/WWGFFebJbeeddy6LLrpoOe200+b4upe85CXlXe96V/nwhz88T3U7mAABAgTmT+Czn/1sOeWUU2Z58cILL1z9jH/9619f9tlnn+pn94033lje9ra3la9//etl7bXXbulk+f3j4IMPLueff351/G9+85uyzTbblC9/+ctlgw02aKkOBxEgQIBAdwUEuN31djYCBPpIYPr06WWnnXYqDz/8cHnLW95SBbb33ntvufjii8uMGTPKeeedV17wghf0UY91hQABAgTaIfC3v/2tvPGNbywLLbRQedOb3lSWW2658te//rVceuml5clPfnK5/PLLq5vyKXvttVd1bP4IcNuhrw4CBAj0nkBmTO27777lsssuKwlVE9ouueSSJb+P5Mb7IossUi655JLy7Gc/u+XOCXBbpnIgAQIEuiZQB7iHHHLI0Dkff/zxKmxNWPvmN7+5HHfccfMV4J511lnl05/+dLn11luruv/4xz+WQw89tPzf//2fCQhdG2EnIkCAwLwJCHDnzcvRBAgQqAT+97//le2226489NBD5cILLywrrbTSkExm5SbQXWyxxcq3v/1tYgQIECBAYBaBPFV/wgknlGuvvXaW9488Fb/VVluV3FTff//9Z1MT4LqQCBAgMJgCZ599djnqqKPKYYcdVt773vfOgpBVgPIw0EYbbTTbrK25aQlwB/Na0msCBJotUAe4eUBnZPn4xz9ePfD505/+tPzhD3+Y5xm4IwPcZktoHQECBAhEQIDrOiBAgMB8CFx33XVll112KSeddFI1K2pkSXD74x//uOy3337V0/H//e9/S268ZMmzP//5z+VZz3pW2W233co73vGOoZdmmczdd9+9Wk7zF7/4RbWc5nve856y5557VsfUy9vkicksdfanP/2prLbaatU5XvOa1wzVk9m/OeaKK66oZge/6EUvKnl6c6211qqOufnmm6ubPJ/5zGfKMcccU+1/8tWvfrWsssoq8yHhJQQIECAwrwLHHnts9fDPtGnTSpZEG17y833ZZZctb33rW6svz2kJ5dzAueGGG8rVV189y+vz83311Vcvxx9/fMmMrSzzn2XS7rnnnrLqqqtWy2AmJK7LuuuuWz7ykY9U7085Jm3LUmoKAQIECDRD4Iknnigvf/nLy3Of+9xy7rnnjtqo/Jz/7W9/W4488sih73/ve98rX/jCF8rvfve7ssQSS5Rtt922fPSjH62W3kwZGeDefffd5Ygjjqh+h1l66aXLgQceWA4//HBLKDfjMtAKAgQGRGBuAW7C29z/+drXvlbdYxq+hHI+95955pnVinCZWZvfMfJgT+4F5XeDPAiae0B1+dSnPlUtvTx8CeXce8rvIYsvvnh1jtxbetnLXlY9QFRv8TLawz95D8pM3jp0zu8vWer5Rz/6UfUn9eX+2dZbb13N9v3Zz35WrTZ0wAEHlC233HJARlY3CRAgMH8CAtz5c/MqAgQGXCDB5znnnFPtQ/jUpz51TI3caM9yN1kKMzfLM+sqSyznxkiC3JR8yH3kkUeqD7HvfOc7q6cqsxfiBz/4wbLHHnsMBbhLLbVUNXMre+1OmTKl+hCeG+8JajMzeMcddyy33357dYMm+6TkRk9C22984xtVcFwHuPlePqAn5E27FAIECBDojkDeO7bffvuyySabVEvxb7zxxtWqDaOVOQW4P/jBD6qb79/85jfL85///OqluVnz6le/ugpts7xm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uD112WgsAQIDLpDfEaZOnVrtRZgHNhPiZvWFd7/73dWKPMNLHurJHop33nlnefrTn179LpAgNu83KSP3MczvKFlqM7Oocsxee+1VPQSUm/wC3AG/8HSfAIGuCcxPgLv22muX7Jeen+F5YDN73+aBzR122KFsueWW5fTTT6+WWM69oiy3n9Xdcs9q0003necAN79fZHJAgtyEvVlt7vWvf331nmEGbtcuEyciQGCABAS4AzTYukqAQLMFRu4TMrK1I5e3aXZvtI4AAQIECBAgQIAAAQIECBAgQIAAAQIECBCYHwEB7vyoeQ0BAgQ6ICDA7QCqKgkQIECAAAECBAgQIECAAAECBAgQIECAQI8JCHB7bMA0lwCB/hUQ4Pbv2OoZAQIECBAgQIAAAQIECBAgQIAAAQIECBBoVUCA26qU4wgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQINBhAQFuh4FVT4AAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAgVYFBLitSjmOAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECHRYQ4HYYWPUECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBoVUCA26qU4wgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQINBhAQFuh4FVT4AAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAgVYFBLitSjmOAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECHRYQ4HYYWPUECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBoVUCA26qU4wgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQINBhAQFuh4FVT4AAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAgVYFBLitSjmOAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECHRYQ4HYYWPUECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBoVeD/AU+qCdSzpu/5AAAAAElFTkSuQmCC\"></div>\n" +
"<div id=\"window-resizer-tooltip\"><a href=\"#\" title=\"Edit settings\"></a><span class=\"tooltipTitle\">Window size: </span><span class=\"tooltipWidth\" id=\"winWidth\"></span> x <span class=\"tooltipHeight\" id=\"winHeight\"></span><br><span class=\"tooltipTitle\">Viewport size: </span><span class=\"tooltipWidth\" id=\"vpWidth\"></span> x <span class=\"tooltipHeight\" id=\"vpHeight\"></span></div></body></html>";
            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(htmlTag));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
